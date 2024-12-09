package org.sonatype.nexus.repository.rest.api;

import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.sonatype.nexus.common.app.ApplicationVersion;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.group.GroupHandler;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.rest.api.model.AbstractApiRepository;
import org.sonatype.nexus.repository.rest.api.model.GroupAttributes;
import org.sonatype.nexus.repository.rest.api.model.GroupDeployAttributes;
import org.sonatype.nexus.repository.rest.api.model.GroupRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.SimpleApiGroupRepository;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.validation.ConstraintViolationFactory;
import org.sonatype.nexus.validation.Validate;

import com.google.common.collect.Sets;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;
import static org.sonatype.nexus.validation.ConstraintViolations.maybePropagate;

/**
 * @since 3.20
 */
public abstract class AbstractGroupRepositoriesApiResource<T extends GroupRepositoryApiRequest>
    extends AbstractRepositoriesApiResource<T>
{
  private static final String WRITABLE_MEMBER = "writableMember";

  private ConstraintViolationFactory constraintViolationFactory;

  private RepositoryManager repositoryManager;

  private ApplicationVersion applicationVersion;

  @Inject
  public void setConstraintViolationFactory(final ConstraintViolationFactory constraintViolationFactory) {
    this.constraintViolationFactory = checkNotNull(constraintViolationFactory);
  }

  @Inject
  public void setRepositoryManager(final RepositoryManager repositoryManager) {
    this.repositoryManager = checkNotNull(repositoryManager);
  }

  @Inject
  public void setApplicationVersion(final ApplicationVersion applicationVersion) {
    this.applicationVersion = applicationVersion;
  }

  @POST
  @RequiresAuthentication
  @Validate
  public Response createRepository(final T request) {
    validateRequest(request);
    return super.createRepository(request);
  }

  @PUT
  @Path("/{repositoryName}")
  @RequiresAuthentication
  @Validate
  public Response updateRepository(
      final T request,
      @PathParam("repositoryName") final String repositoryName)
  {
    validateRequest(request);
    return super.updateRepository(request, repositoryName);
  }

  private void validateRequest(T request) {
    String groupFormat = request.getFormat();
    Set<ConstraintViolation<?>> violations = Sets.newHashSet();
    Collection<String> memberNames = request.getGroup().getMemberNames();
    for (String repositoryName : memberNames) {
      Repository repository = repositoryManager.get(repositoryName);
      if (nonNull(repository)) {
        String memberFormat = repository.getFormat().getValue();
        if (!memberFormat.equals(groupFormat)) {
          violations.add(constraintViolationFactory.createViolation("memberNames",
              "Member repository format does not match group repository format: " + repositoryName));
        }
      }
      else {
        violations.add(constraintViolationFactory.createViolation("memberNames",
            "Member repository does not exist: " + repositoryName));
      }
    }
    validateGroupDeploy(request, violations);
    maybePropagate(violations, log);
  }

  private void validateGroupDeploy(T request, Set<ConstraintViolation<?>> violations) {
    GroupAttributes group = request.getGroup();
    if (group instanceof GroupDeployAttributes) {
      GroupDeployAttributes groupDeployAttributes = (GroupDeployAttributes) group;
      final String writableMember = groupDeployAttributes.getWritableMember();
      if (writableMember == null || writableMember.isEmpty()) {
        return;
      }
      Repository writableMemberRepository = repositoryManager.get(writableMember);
      if (!"PRO".equals(applicationVersion.getEdition())) {
        violations.add(constraintViolationFactory.createViolation(
            WRITABLE_MEMBER,
            GroupHandler.INSUFFICIENT_LICENSE
        ));
      }
      else if (writableMemberRepository == null) {
        violations.add(constraintViolationFactory.createViolation(
            WRITABLE_MEMBER,
            "Writable member repository does not exist"
        ));
      }
      else if (!writableMemberRepository.getType().getValue().equals(HostedType.NAME)) {
        violations.add(constraintViolationFactory.createViolation(
            WRITABLE_MEMBER,
            "Writable member must be a hosted repository"
        ));
      }
      else if (!writableMemberRepository.getFormat().getValue().equals(request.getFormat())) {
        violations.add(constraintViolationFactory.createViolation(
            WRITABLE_MEMBER,
            "Writable member repository format does not match group repository format: " + writableMember
        ));
      }
      else if (!request.getGroup().getMemberNames().contains(writableMember)) {
        violations.add(constraintViolationFactory.createViolation(
            WRITABLE_MEMBER,
            "Writable member must be a member of the group"
        ));
      }
    }
  }

  @GET
  @Path("/{repositoryName}")
  @RequiresAuthentication
  @Validate
  @ApiOperation(value = "Get repository", response = SimpleApiGroupRepository.class)
  @Override
  public AbstractApiRepository getRepository(@ApiParam(hidden = true) @BeanParam final FormatAndType formatAndType,
                                             @PathParam("repositoryName") final String repositoryName)
  {
    return super.getRepository(formatAndType, repositoryName);
  }
}
