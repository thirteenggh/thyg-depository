package org.sonatype.nexus.security.privilege;

import java.util.List;

import org.sonatype.nexus.formfields.FormField;
import org.sonatype.nexus.security.config.CPrivilege;
import org.sonatype.nexus.security.privilege.rest.ApiPrivilege;
import org.sonatype.nexus.security.privilege.rest.ApiPrivilegeRequest;

import org.apache.shiro.authz.Permission;

/**
 * Privilege descriptor.
 */
public interface PrivilegeDescriptor<T extends ApiPrivilege, Y extends ApiPrivilegeRequest>
{
  String getType();

  String getName();

  Permission createPermission(CPrivilege privilege);

  List<FormField> getFormFields();

  T createApiPrivilegeImpl(Privilege privilege);

  void validate(Y apiPrivilege);
}
