package com.whizdm.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.HashMap;
import java.util.HashSet;


@Getter
@Setter
@ToString
public class Variables {

    int countOfForgotPan;
    
    int countOfAmountChanged;
    
    int countOfTenureChanged;
    
    int countOfAmountAfterContinue;
    
    int countOfTenureAfterContinue;
    
    int countOfCurrentAddressDoc;
    
    int countOfAgeRestriction;
    
    int countOfInvalidBank;
    
    int countOfInvalidLocation;
    
    int countOfInvalidSalary;
    
    int countOfItr;

    int countOfEducationReject;
    
    int countOfLoanPurpose;
    
    int countOfLowSalary;
    
    int countOfNoNetbanking;
    
    String docType;
    
    String idType;
    
    Boolean resubmitDocs;
    
    int countOfLoanApps;
    
    int countOfSalaryChanged;
    
    int countOfEmpChanged;
    
    HashSet<String> attributionIds=new HashSet<>();
    
    String planSelectorTimestamp ;
    
    String tempSalary ;
    
    String tempEmpType ;
    
    String initialEmpType;

    int countOfUploadError;

    int countOfPincodeChanged;

    String tempPincode;

    String companyListed;

    int countOfVerificationAttempts;

    int countOfDobChanged;

    String tempDob;

    String incomeVerification;

    Boolean submitFlag;

    HashSet<String> sessionIds=new HashSet<>();

    String initialTimestamp;

    String submitTimestamp;

    HashMap<Integer,HashSet<String>> sessionsForScreen=new HashMap<>();

    HashSet<String> attributionIdsAfterLoan=new HashSet<>();

    HashSet<String> attributionIdsAfterLoan1=new HashSet<>();

    String loanOverviewTimestamp;

    String nachStatus;

    int countOfEmiDebit;

    int countOfEmiNetbanking;

    String loanAgreementStartTime;

    String loanAgreementEndTime;

    String enachStartTime;

    String enachEndTime;

    int countEnach;

    HashMap<Integer,Double> timeSpentOnScreens;

    String prevTimestamp;

    Double timeSpentOnAadharFront;

    Double timeSpentOnAadharBack;

    Double timeSpentOnAadhar;

    Double timeSpentOnCaBackUpload;

    Double timeSpentOnCaFrontUpload;

    Double timeSpentOnChequeUpload;

    Double timeSpentOnItrUpload;

    Double timeSpentOnPanUpload;

    String recentEmpType;

    String recentSalary;

    HashSet<String> docBanks=new HashSet<>();

    Boolean verifyLaterClicked;

    HashSet<String> incomeVerificationOptions=new HashSet<>();

    Double initLoanAmt;

    Double initLoanTenure;

    Double loanAmtPercentage;

    Double loanTenurePercentage;

    HashSet<String> loanAppNos;

}
