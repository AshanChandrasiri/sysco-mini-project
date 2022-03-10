package com.sysco.miniproject.shared;

public class Constants {

    public enum NETWORK_ERROR_CODES {
        NOT_FOUND(404), BAD_REQUEST(400), SERVER_ERROR(500);

        private final int id;

        public int getValue() {
            return id;
        }

        NETWORK_ERROR_CODES(int id) {
            this.id = id;
        }
    }

    public enum ROLES{
        ROLE_ADMIN, ROLE_USER
    }


}
