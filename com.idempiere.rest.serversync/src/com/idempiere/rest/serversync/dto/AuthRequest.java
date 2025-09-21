package com.idempiere.rest.serversync.dto;

/**
 * Authentication request POJO.
 * We avoid maps; primitives/strings are used for clarity.
 */
public class AuthRequest {
    private String userName;
    private String password;
    private Parameters parameters;

    public AuthRequest() {}
    public AuthRequest(String userName, String password, int clientId, int roleId, int orgId, String language) {
        this.userName = userName;
        this.password = password;
        this.parameters = new Parameters(clientId, roleId, orgId, language);
    }

    // getters & setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Parameters getParameters() { return parameters; }
    public void setParameters(Parameters parameters) { this.parameters = parameters; }
    
    public class Parameters {
        private int clientId;
        private int roleId;
        private int organizationId;
        private String language;

        public Parameters() {}
        public Parameters(int clientId, int roleId, int organizationId, String language) {
            this.clientId = clientId;
            this.roleId = roleId;
            this.organizationId = organizationId;
            this.language = language;
        }

        // getters & setters
        public int getClientId() { return clientId; }
        public void setClientId(int clientId) { this.clientId = clientId; }
        public int getRoleId() { return roleId; }
        public void setRoleId(int roleId) { this.roleId = roleId; }
        public int getOrganizationId() { return organizationId; }
        public void setOrganizationId(int organizationId) { this.organizationId = organizationId; }
        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }
    }
}
