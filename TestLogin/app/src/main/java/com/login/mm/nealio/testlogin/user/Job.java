package com.login.mm.nealio.testlogin.user;

/**
 *
 * Created by ndw6152 on 5/22/2017.
 */

public class Job {

    private String summary;
    private String description;

    private boolean onSiteDiagnostic = false;
    private boolean carInWorkingCondition = false;
    private boolean repairCanBeDoneOnSite = false;
    private boolean carPickUpDropOff = false;

    public Job() {

    }

    public Job (String summary, String description, boolean onSiteDiagnostic, boolean carInWorkingCondition, boolean repairCanBeDoneOnSite, boolean carPickUpDropOff) {
        this.summary = summary;
        this.description = description;
        this.onSiteDiagnostic = onSiteDiagnostic;
        this.carInWorkingCondition = carInWorkingCondition;
        this.repairCanBeDoneOnSite = repairCanBeDoneOnSite;
        this.carPickUpDropOff = carPickUpDropOff;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOnSiteDiagnostic() {
        return onSiteDiagnostic;
    }

    public void setOnSiteDiagnostic(boolean onSiteDiagnostic) {
        this.onSiteDiagnostic = onSiteDiagnostic;
    }

    public boolean isCarInWorkingCondition() {
        return carInWorkingCondition;
    }

    public void setCarInWorkingCondition(boolean carInWorkingCondition) {
        this.carInWorkingCondition = carInWorkingCondition;
    }

    public boolean isRepairCanBeDoneOnSite() {
        return repairCanBeDoneOnSite;
    }

    public void setRepairCanBeDoneOnSite(boolean repairCanBeDoneOnSite) {
        this.repairCanBeDoneOnSite = repairCanBeDoneOnSite;
    }

    public boolean isCarPickUpDropOff() {
        return carPickUpDropOff;
    }

    public void setCarPickUpDropOff(boolean carPickUpDropOff) {
        this.carPickUpDropOff = carPickUpDropOff;
    }
}
