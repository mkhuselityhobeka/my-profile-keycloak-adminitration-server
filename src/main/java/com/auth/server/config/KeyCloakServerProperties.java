package com.auth.server.config;

import org.eclipse.microprofile.config.inject.ConfigProperties;
/*
 setting up keycloak server properties
 */
@ConfigProperties(prefix = "keycloak.server")
public class KeyCloakServerProperties {

    String contextPath="/auth";
    String realmImportFile = "my-profile-realm.json";
    AdminUser adminUser = new AdminUser();

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setRealmImportFile(String realmImportFile) {
        this.realmImportFile = realmImportFile;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getRealmImportFile() {
        return realmImportFile;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }
}
