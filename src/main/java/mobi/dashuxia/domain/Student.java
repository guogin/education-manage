package mobi.dashuxia.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Student {
    private String id;
    private String birthday;
    @NonNull
    private String studentName;
    private boolean isChild;
    private int classPeriod;
    private int leftPeriods;
    private int donePeriods;
    private String parentName;
    private String mobilePhone;
    @NonNull
    private Long customerId;

    public Student() {
    }

    public Student(String id, String name, String birthday, Long customerId,
            Integer classPeriod, Integer donePeriods, Integer leftPeriods,
            Boolean isChild, String parentName, String mobilePhone) {
        this.id = id;
        this.studentName = name;
        this.birthday = birthday;
        this.customerId = customerId;
        this.classPeriod = classPeriod;
        this.donePeriods = donePeriods;
        this.leftPeriods = leftPeriods;
        this.isChild = isChild;
        this.parentName = parentName;
        this.mobilePhone = mobilePhone;
    }
}
