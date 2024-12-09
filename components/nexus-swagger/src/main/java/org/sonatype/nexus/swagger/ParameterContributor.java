package org.sonatype.nexus.swagger;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.sonatype.goodies.common.ComponentSupport;

import com.google.common.annotations.VisibleForTesting;
import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.AbstractSerializableParameter;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

/**
 * A custom {@link SwaggerContributor} that contributes parameters to the {@link Swagger}
 * definition for a given {@link HttpMethod} for all the paths provided.
 *
 * @since 3.7
 */
public abstract class ParameterContributor<T extends AbstractSerializableParameter>
    extends ComponentSupport
    implements SwaggerContributor
{
  private final Collection<HttpMethod> httpMethods;

  private final Collection<String> paths;

  private final Collection<T> params;

  @VisibleForTesting
  final Map<String, Boolean> contributed;

  private boolean allContributed;

  public ParameterContributor(final Collection<HttpMethod> httpMethods,
                              final Collection<String> paths,
                              final Collection<T> params)
  {
    this.httpMethods = checkNotNull(httpMethods);
    this.paths = checkNotNull(paths);
    this.params = checkNotNull(params);
    this.contributed = httpMethods.stream()
        .flatMap(httpMethod -> paths.stream().map(path -> getKey(httpMethod, path)))
        .collect(toMap(p -> (String) p, p -> false));
  }

  @Override
  public void contribute(final Swagger swagger) {
    if (allContributed) {
      return;
    }

    for (HttpMethod httpMethod : httpMethods) {
      for (String path : paths) {
        contributed.compute(getKey(httpMethod, path),
            (key, value) -> value || contributeGetParameters(swagger, httpMethod, path, params));
      }
    }

    allContributed = contributed.entrySet().stream().allMatch(Entry::getValue);
  }

  private boolean contributeGetParameters(final Swagger swagger,
                                          final HttpMethod httpMethod,
                                          final String path,
                                          final Collection<T> parameters)
  {
    boolean contrib = false;
    Optional<Operation> operation = getOperation(swagger, httpMethod, path);
    if (operation.isPresent()) {
      final Operation op = operation.get();
      parameters.forEach(param -> {
        if (!op.getParameters().contains(param)) {
          log.debug("adding {}, method: {}, path: {}, parameter: {}",
              param.getClass().getSimpleName(), httpMethod, path, param.getName());
          op.addParameter(param);
        }
      });
      contrib = true;
    }
    return contrib;
  }

  private Optional<Operation> getOperation(final Swagger swagger, final HttpMethod httpMethod, final String path) {
    return Optional.ofNullable(swagger.getPaths())
        .orElseGet(Collections::emptyMap)
        .entrySet().stream()
        .filter(e -> path.equals(e.getKey()))
        .findFirst()
        .map(Entry::getValue)
        .map(Path::getOperationMap)
        .map(m -> m.get(httpMethod));
  }

  private static String getKey(final HttpMethod httpMethod, final String path) {
    return format("%s-%s", httpMethod.name(), path);
  }
}
