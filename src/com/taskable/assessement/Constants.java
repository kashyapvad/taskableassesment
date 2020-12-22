package com.taskable.assessement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Constants {

    private Constants() {}

    public static final List<String> RuleClasses = Collections.unmodifiableList(
            new ArrayList<>() {{
                add("com.taskable.assessement.rules.MembershipRule");
                add("com.taskable.assessement.rules.PhysicalItemRule");
            }});
}
