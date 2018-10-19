package cloud.tninis.employeegifts;

import java.util.HashMap;
import java.util.Map;

public enum TypeOfEmployee {
    M(1),
    O(2);

    private int value;
    private static Map map = new HashMap<>();

    private TypeOfEmployee(int value) {
        this.value = value;
    }

    static {
        for (TypeOfEmployee employeeType : TypeOfEmployee.values()) {
            map.put(employeeType.value, employeeType);
        }
    }

    public static TypeOfEmployee valueOf(int employeeType) {
        return (TypeOfEmployee) map.get(employeeType);
    }

    public int getValue() {
        return value;
    }
}