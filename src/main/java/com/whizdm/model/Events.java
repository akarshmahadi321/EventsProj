package com.whizdm.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aws_events_loans_v2")
@Getter
@Setter
@ToString
public class Events {
    @Id
    @Column(name = "event_type")
    String eventType;
    @Column(name = "event_timestamp")
    String eventTimestamp;
    //Date eventTimestamp = new Date();
    @Column(name = "arrival_timestamp")
    String arrivalTimestamp;
    //Date arrivalTimestamp = new Date();
    @Column(name = "event_version")
    String eventVer;
    @Column(name = "application_package_name")
    String appPackageName;
    @Column(name = "application_version_name")
    String appVerName;
    @Column(name = "application_title")
    String appTitle;
    @Column(name = "device_make")
    String deviceMake;
    @Column(name = "device_model")
    String deviceModel;
    @Column(name = "device_platform_version")
    String devicePlatformVer;
    @Column(name = "device_locale_language")
    String deviceLocaleLanguage;
    @Column(name = "device_locale_country")
    String deviceLocaleCountry;
    @Column(name = "session_id")
    String sessionId;
    @Column(name = "session_start_timestamp")
    String sessionStartTimestamp;
    //Date sessionStartTimestamp = new Date();
    @Column(name = "session_stop_timestamp")
    String sessionStopTimestamp;
    //Date sessionStopTimestamp = new Date();
    @Column(name = "attributes")
    String attributes;
    @Column(name = "android_id")
    String androidId;
    @Column(name = "attribution_id")
    String attributionId;
    @Column(name = "ad_id")
    String adId;
    @Column(name = "user_id")
    String userId;
    @Column(name = "loan_application_no")
    String loanAppNo;
    @Column(name = "created_at")
    String createdAt;
    //Date createdAt = new Date();
    @Column(name = "application_version_code")
    String appVerCode;



}
