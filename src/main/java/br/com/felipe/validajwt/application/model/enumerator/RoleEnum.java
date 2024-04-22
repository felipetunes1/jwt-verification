package br.com.felipe.validajwt.application.model.enumerator;

public enum RoleEnum {
    ADMIN("Admin"), MEMBER("Member"), EXTERNAL("External");
    

    public final String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public static RoleEnum findRole(String value) {
        if(ADMIN.name.equals(value))
            return ADMIN;
        if(MEMBER.name.equals(value))
            return MEMBER;
        if(EXTERNAL.name.equals(value))
            return EXTERNAL;

        return null;
    }
}
