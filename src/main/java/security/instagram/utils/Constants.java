package security.instagram.utils;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
public final class Constants {
    public static final String USER_ID_CLAIMS_NAME = "user_id";
    public static final String ROLE_CLAIMS_NAME = "role";
    public static final String USER_NAME_CLAIMS_NAME = "user_name";
    public static final String ACCESS_TOKEN_ID_CLAIMS_NAME = "ati";
    public static final String SEPARATE = "==============================================";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "token_type";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";

    public static final String REFRESH_TOKEN_PARAM = "refresh-token";

    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String ACCOUNT_TYPE_CLAIMS_NAME = "account_type";
    public static final String ACCESS_TOKEN_NOT_FOUND_ERROR = "accessTokenNotFound";
    public static final String IP_ADDRESS_NOT_NULL_ERROR = "ipAddressMustNotNull";
    public static final String IP_ADDRESS = "ipAddress";
    public static final String INVALID_TOKEN="Invalid Token";
    public static final String EXPIRED_ACCESS_TOKEN = "Expired access token";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String TOKEN_INVALID_USER_ID = "Invalid User Id";
    public static final String TOKEN_INVALID_USER_NAME = "Invalid user name";
    public static final String TOKEN_INVALID_ROLE = "Invalid role";
}