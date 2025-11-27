import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RepairBill implements Serializable {
    private static final long serialVersionUID = 1L;

    private String firm;
    private String workType;
    private String unit;
    private double pricePerUnit;
    private LocalDate executionDate;
    private double volume;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public RepairBill(String firm, String workType, String unit,
                      double pricePerUnit, LocalDate executionDate, double volume) {
        this.firm = firm;
        this.workType = workType;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
        this.executionDate = executionDate;
        this.volume = volume;
    }

    public String getFirm() {
        return firm;
    }

    public String getWorkType() {
        return workType;
    }

    public String getUnit() {
        return unit;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public double getVolume() {
        return volume;
    }

    public double getTotalCost() {
        return pricePerUnit * volume;
    }

    public static LocalDate parseDate(String s) throws DateTimeParseException {
        return LocalDate.parse(s, DATE_FORMAT);
    }

    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMAT);
    }

    @Override
    public String toString() {
        return "RepairBill{" +
                "firm='" + firm + '\'' +
                ", workType='" + workType + '\'' +
                ", unit='" + unit + '\'' +
                ", pricePerUnit=" + pricePerUnit +
                ", executionDate=" + formatDate(executionDate) +
                ", volume=" + volume +
                ", totalCost=" + getTotalCost() +
                '}';
    }
}