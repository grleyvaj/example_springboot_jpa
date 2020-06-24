package cu.example.interfaces.shared;

import org.springframework.stereotype.Component;

@Component
public final class URIConstant {

    public static final String ENTITY_API = "/univ";

    public static final String API_VERSION = "/v1";

    public static final String URI_ENROLL = "/students";
//    public static final String URI_ENROLL_PAG = "/students-list";
    public static final String ENTITY_NAME_ENROLL = "Student";

    public static final String URI_FORM = "/formations";
//    public static final String ENTITY_NAME_FORM = "Formation";

    public static final String URI_TUTOR = "/tutors";
//    public static final String ENTITY_NAME_TUTOR = "Tutor";
}
