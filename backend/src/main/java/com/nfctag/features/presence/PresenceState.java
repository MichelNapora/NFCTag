package com.nfctag.features.presence;

/** The states an intervention can be filtered on. The front sends these exact values. */
public final class PresenceState {

    public static final String ALL       = "all";
    public static final String ONGOING   = "ongoing";
    public static final String DONE      = "done";
    public static final String ESTIMATED = "estimated";
    public static final String SUSPECT   = "suspect";

    private PresenceState(){}
}