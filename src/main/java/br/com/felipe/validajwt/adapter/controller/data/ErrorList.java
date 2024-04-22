package br.com.felipe.validajwt.adapter.controller.data;

import java.util.ArrayList;

public class ErrorList extends ArrayList<JwtError> {

    public final static JwtError AUTHORIZATION_IS_NULL = new JwtError("Authorization is Null", "00000001");
    public final static JwtError AUTHORIZATION_IS_BLANK = new JwtError("Authorization is Blank", "00000002");
    public final static JwtError AUTHORIZATION_LENGTH_ERROR = new JwtError("Authorization not contains correct length", "00000003");
    public final static JwtError DECODE_BASE_64_ERROR = new JwtError("Error On Decode Claims", "00000004");
    public final static JwtError PARSE_OBJECT_ERROR = new JwtError("Error On Parse To Claims Object", "00000005");
    public static final JwtError INVALID_ROLE = new JwtError("Role is Invalid", "00000006");
    public static final JwtError NAME_IS_NULL = new JwtError("Name is Null", "00000007");
    public static final JwtError NAME_IS_BLANK = new JwtError("Name is Blank", "00000008");
    public static final JwtError INVALID_NAME = new JwtError("Invalid Name", "00000009");
    public static final JwtError SEED_IS_NULL = new JwtError("Seed is Null", "00000010");
    public static final JwtError ROLE_IS_NULL = new JwtError("Role is Null", "00000011");
    public static final JwtError CONTAINS_CLAIMS = new JwtError("Contains Claims Not Expected", "00000012");
    public static final JwtError CLAIMS_FORMAT_INCORRECT = new JwtError("Claims WIth Format Incorrect", "00000013");
    public static final JwtError SEED_IS_INVALID = new JwtError("Seed is Invalid", "00000014");

    
}
