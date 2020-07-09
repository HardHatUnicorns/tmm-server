package pl.aogiri.tmm.server.util.enums;

public enum Privileges {
    //Authentication
    LOGIN,

    //Roles
    ROLE_READ,
    ROLE_CREATE,
    ROLE_UPDATE,
    ROLE_DELETE,
    ROLE_ADD_PRIVILEGE,
    ROLE_REMOVE_PRIVILEGE,

    //Users
    USER_READ,
    USER_UPDATE,
    USER_DELETE,
    USER_ADD_ROLE,
    USER_REMOVE_ROLE,

}
