package com.whizdm;

import com.opencsv.CSVWriter;
import com.whizdm.model.Variables;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


@Component
public class Action {
    public void writeToCsv(List<Object[]> eventsList) throws ParseException {
        HashMap<Integer, HashSet<String>> screens = new HashMap<>();
        String getLoanOffer = "get_loan_offer_screen_shown,employment_type_selected,salary_bank_clicked,salary_bank_screen_shown,salary_bank_selected,get_loan_offer_screen_continue";
        HashSet<String> mySet = new HashSet(Arrays.asList(getLoanOffer.split(",")));
        screens.put(1, mySet);
        String infoScreen = "personal_info_screen_shown,forgot_pan_clicked,company_name_popup,company_name_done,personal_info_screen_continue,user_info_screen_shown,forgot_pan_clicked,company_name_popup,company_name_done,user_info_screen_continue";
        HashSet<String> mySet1 = new HashSet(Arrays.asList(infoScreen.split(",")));
        screens.put(2, mySet1);
        String planSelectorScreen = "loan_eligible_popup,loan_eligible_popup_continue,plan_selector_screen_shown,loan_amount_changed,loan_tenure_changed,plan_selector_continue";
        HashSet<String> mySet2 = new HashSet(Arrays.asList(planSelectorScreen.split(",")));
        screens.put(3, mySet2);
        String uploadDocsScreen = "upload_docs_screen_shown,kyc_status,id_proof_type,pan_upload_tutorial_shown,pan_upload_tutorial_continue,pan_card_uploaded,aadhaar_front_upload_tutorial_shown,aadhaar_back_upload_tutorial_shown,aadhaar_upload_tutorial_continue,aadhaar_flip_doc_popup,aadhaar_flip_doc_popup_continue,doc_aadhaar_front_uploaded,doc_aadhaar_back_uploaded,doc_aadhaar_address_check,current_address_document_selected,doc_ca_front_uploaded,doc_ca_back_uploaded,doc_imps_verify_clicked,doc_imps_verify_retry_clicked,doc_bank_acc_verified,doc_cheque_uploaded,cheque_upload_tutorial_shown,cheque_upload_tutorial_upload,doc_photo_uploaded,photo_upload_tutorial_shown,doc_video_uploaded,video_upload_tutorial_shown,doc_itr_uploaded_1,doc_itr_email_later_selected,upload_docs_imcomplete_error_continue";
        HashSet<String> mySet3 = new HashSet(Arrays.asList(uploadDocsScreen.split(",")));
        screens.put(4, mySet3);


        String header = "UserIds,CountOfForgotPanClicked,CountOfLoanAmountChanged,CountOfLoanTenureChanged,CountOfAmountChangedAfterContinue,CountOfTenureChangedAfterContinue,CountOfCurrentAddressDoc,CountOfAgeRestrictionReject,CountOfInvalidBankReject,CountOfInvalidLocationReject,CountOfInvalidSalaryReject,CountOfItrReject,CountOfLoanPurposeReject,CountOfLowSalaryReject,CountOfNoNetbankingReject,CountOfEducationLevelReject,IdType,AddressProofType,CountOfLoanApplications,CountOfSalaryChanged,CountOfEmployementChanged,ChangedFromInitialEmpType,CountOfEducationLevelRejectsResubmitDocsClicked,CountOfAttributionIds,CountOfUploadDocsError,CountOfPincodeChanged,CompanyListedStatus,CountOfVerificationAttempts,CountOfDobChanged,IncomeVerificationMethod,SessionsToSubmit,TimeToSubmitInMins,SessionIdsInGetLoanOfferScreen,SessionIdsInInfoScreen,SessionIdsInPlanSelectorScreen,SessionIdsInUploadDocScreen,TimeSpentOnLoanOfferScreen,TimeSpentOnInfoScreen,TimeSpentOnPlanSelectorScreen,TimeSpentOnUploadDocScreen,AttributionIdsAfterLoanOverview,AttributionIdsAfterLoanOverview1Month,MostRecentNachStatus,CountOfEmiDebit,CountOfEmiNetbanking,LoanAgreementTime,EnachTime,TimeSpentOnAadharFront,TimeSpentOnAadhar,TimeSpentOnCaBackUpload,TimeSpentOnCaFrontUpload,TimeSpentOnChequeUpload,TimeSpentOnItrUpload,TimeSpentOnPanUpload,DistinctPdfUploaded,VerifyLaterClicked,IncomeVerificationOptions,RecentSalary,RecentEmpType,LoanAmountPercentage,LoanTenureDifference";
        HashMap<String, Variables> hm = new HashMap<>();
        HashMap<String, HashSet<String>> loanAppsForUsers = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");
        SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String heads[]=header.split(",");
        System.out.println(heads.length);


        Iterator<Object[]> itr = eventsList.iterator();
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(new File("/Users/moneyview/Desktop/output.csv")));
            writer.writeNext(header.split(","));

            while (itr.hasNext()) {
                try {
                    Object[] o = itr.next();


                    String eventType = o[0].toString();
                    String eventTimestamp = o[1].toString();
                    String userId = o[2].toString();
                    String attributes = o[3].toString();
                    String sessionId = o[4].toString();

                    System.out.println(userId + " " + eventTimestamp + " " + eventType + " " + attributes);

                    JSONObject object = new JSONObject(attributes);
                    if (!Pattern.compile("Sync|Incoming|daily_sync|Daily|notification|ab|permission|SMS|sms|Patcher|SimpleLoan").matcher(eventType).find()) {

                        if (!hm.containsKey(userId)) {
                            Variables v = new Variables();
                            v.setPlanSelectorTimestamp(null);
                            v.setInitialEmpType(null);
                            v.setTempSalary(null);
                            v.setTempEmpType(null);
                            v.setResubmitDocs(false);
                            v.setSubmitFlag(false);
                            v.setEnachStartTime(null);
                            v.setEnachEndTime(null);
                            v.setLoanAgreementStartTime(null);
                            v.setLoanAgreementEndTime(null);
                            v.setCountEnach(0);
                            v.setInitialTimestamp(eventTimestamp);
                            v.setTimeSpentOnAadhar(0.0);
                            v.setTimeSpentOnAadharFront(0.0);
                            v.setTimeSpentOnCaFrontUpload(0.0);
                            v.setTimeSpentOnCaBackUpload(0.0);
                            v.setTimeSpentOnChequeUpload(0.0);
                            v.setTimeSpentOnItrUpload(0.0);
                            v.setTimeSpentOnPanUpload(0.0);
                            v.setVerifyLaterClicked(false);
                            v.setInitLoanAmt(0.0);
                            v.setInitLoanTenure(0.0);


                            HashMap<Integer, HashSet<String>> tempMap = new HashMap<>();
                            tempMap.put(1, new HashSet<String>());
                            tempMap.put(2, new HashSet<String>());
                            tempMap.put(3, new HashSet<String>());
                            tempMap.put(4, new HashSet<String>());

                            v.setSessionsForScreen(tempMap);

                            tempMap = v.getSessionsForScreen();

                            for (Map.Entry<Integer, HashSet<String>> me : screens.entrySet()) {
                                int screenNum = me.getKey();
                                HashSet<String> screenEvents = me.getValue();
                                if (screenEvents.contains(eventType)) {
                                    HashSet<String> sessionIds = tempMap.get(screenNum);
                                    sessionIds.add(sessionId);
                                    tempMap.put(screenNum, sessionIds);
                                    break;
                                }
                            }

                            v.setSessionsForScreen(tempMap);

                            HashMap<Integer, Double> hashMap = new HashMap<>();
                            hashMap.put(1, 0.0);
                            hashMap.put(2, 0.0);
                            hashMap.put(3, 0.0);
                            hashMap.put(4, 0.0);

                            v.setTimeSpentOnScreens(hashMap);
                            v.setPrevTimestamp(eventTimestamp);


                            if (v.getSubmitFlag() == false) {

                                HashSet<String> sessionIds = v.getSessionIds();
                                sessionIds.add(sessionId);
                                v.setSessionIds(sessionIds);
                            }

                            HashSet<String> loanApps = new HashSet<>();

                            if (hasKey(object, "loan_application_no")) {
                                loanApps.add(object.getString("loan_application_no"));
                                v.setCountOfLoanApps(1);
                                loanAppsForUsers.put(userId, loanApps);
                            }

                            if (hasKey(object, "salary")) {
                                v.setCountOfSalaryChanged(0);
                                v.setTempSalary(object.getString("salary"));
                                v.setRecentSalary(object.getString("salary"));

                            }
                            if (hasKey(object, "emp_type")) {
                                if (!object.getString("emp_type").equalsIgnoreCase("undefined")) {
                                    v.setCountOfEmpChanged(0);
                                    v.setTempEmpType(object.getString("emp_type"));
                                    v.setInitialEmpType(v.getTempEmpType());
                                    v.setRecentEmpType(object.getString("emp_type"));
                                }
                            }
                            if (hasKey(object, "pincode")) {
                                v.setCountOfPincodeChanged(0);
                                v.setTempPincode(object.getString("pincode"));
                            }
                            if (hasKey(object, "attribution_id")) {
                                v.getAttributionIds().add(object.getString("attribution_id"));
                            }
                            if (hasKey(object, "is_company_name_listed")) {
                                v.setCompanyListed(object.getString("is_company_name_listed"));
                            }
                            if (hasKey(object, "dob")) {
                                v.setCountOfDobChanged(0);
                                v.setTempDob(object.getString("dob"));
                            }
                            if (hasKey(object, "nach_status")) {
                                v.setNachStatus(object.getString("nach_status"));
                            }
                            if (eventType.equals("resubmit_docs_clicked")) {
                                v.setResubmitDocs(true);
                            }
                            if (eventType.equals("pay_emi_via_debit_clicked")) {
                                v.setCountOfEmiDebit(1);
                            }
                            if (eventType.equals("pay_emi_via_netbanking_chosen")) {
                                v.setCountOfEmiNetbanking(1);
                            }

                            if (eventType.equals("forgot_pan_clicked")) {
                                v.setCountOfForgotPan(1);
                            }
                            if (eventType.equals("loan_amount_changed")) {
                                v.setCountOfAmountChanged(1);
                                if(hasKey(object,"amount"))
                                {
                                    if(v.getInitLoanAmt()==0.0)
                                    {
                                        v.setInitLoanAmt(Double.parseDouble(object.getString("amount")));
                                    }
                                    else
                                    {
                                        Double tempPercent=((Double.parseDouble(object.getString("amount"))-v.getInitLoanAmt())/v.getInitLoanAmt())*100;
                                        v.setLoanAmtPercentage(tempPercent);
                                    }
                                }

                            }
                            if (eventType.equals("loan_tenure_changed")) {
                                v.setCountOfTenureChanged(1);
                                if(hasKey(object,"tenure"))
                                {
                                    if(v.getInitLoanTenure()==0.0)
                                    {
                                        v.setInitLoanTenure(Double.parseDouble(object.getString("tenure")));
                                    }
                                    else
                                    {
                                        Double tempPercent=Double.parseDouble(object.getString("tenure"))-v.getInitLoanTenure();
                                        v.setLoanTenurePercentage(tempPercent);

                                    }
                                }
                            }
                            if (eventType.equals("plan_selector_continue")) {
                                if (v.getPlanSelectorTimestamp() == null) {
                                    v.setPlanSelectorTimestamp(eventTimestamp);
                                    v.setCountOfAmountAfterContinue(0);
                                    v.setCountOfTenureAfterContinue(0);
                                }
                            }
                            if (eventType.equals("current_address_document_selected")) {
                                v.setCountOfCurrentAddressDoc(1);
                            }
                            if (eventType.equals("reject_age_restriction_screen_shown")) {
                                v.setCountOfAgeRestriction(1);
                            }
                            if(eventType.equals("reject_education_level_screen_shown"))
                            {
                                v.setCountOfEducationReject(1);
                            }
                            if (eventType.equals("reject_invalid_bank_screen_shown")) {
                                v.setCountOfInvalidBank(1);
                            }
                            if (eventType.equals("reject_invalid_location_screen_shown")) {
                                v.setCountOfInvalidLocation(1);
                            }
                            if (eventType.equals("reject_invalid_salary_mode_screen_shown")) {
                                v.setCountOfInvalidSalary(1);
                            }
                            if (eventType.equals("reject_itr_screen_shown")) {
                                v.setCountOfItr(1);
                            }
                            if (eventType.equals("reject_loan_purpose_screen_shown")) {
                                v.setCountOfLoanPurpose(1);
                            }
                            if (eventType.equals("reject_low_salary_screen_shown")) {
                                v.setCountOfLowSalary(1);
                            }
                            if (eventType.equals("reject_no_netbanking_screen_shown")) {
                                v.setCountOfNoNetbanking(1);
                            }
                            if (eventType.equals("id_proof_type")) {
                                v.setIdType(object.getString("selected_id_proof"));
                            }
                            if (eventType.equals("doc_pan_uploaded")) {
                                v.setIdType("PAN");
                            }
                            if (eventType.equals("doc_aadhaar_front_uploaded")) {
                                v.setIdType("AADHAR");
                            }
                            if (eventType.equals("doc_aadhaar_back_uploaded")) {
                                v.setIdType("AADHAR");
                            }
                            if (eventType.equals("current_address_document_selected")) {
                                v.setDocType(object.getString("address_doc_type"));
                            }
                            if (eventType.equals("resubmit_docs_clicked")) {
                                v.setResubmitDocs(true);
                            }
                            if (eventType.equals("upload_docs_incomplete_error_popup")) {
                                v.setCountOfUploadError(1);
                            }
                            if (eventType.equals("docs_imps_verify_retry_clicked")) {
                                v.setCountOfVerificationAttempts(1);
                            }
                            if (eventType.equals("docs_imps_verify_clicked")) {
                                v.setCountOfVerificationAttempts(1);
                            }
                            if (eventType.equals("verify_via_netbanking_screen_shown")) {
                                v.setIncomeVerification("Netbanking");
                            }
                            if (eventType.equals("verify_via_pdf_upload_continue")) {
                                v.setIncomeVerification("Pdf");
                            }
                            if (eventType.equals("email_bank_statement_continue")) {
                                v.setIncomeVerification("Email");
                            }
                            if (eventType.equals("loan_review_submit_clicked")) {
                                v.setSubmitTimestamp(eventTimestamp);
                                v.setSubmitFlag(true);
                            }
                            if (eventType.equals("loan_overview_screen_shown")) {
                                v.setLoanOverviewTimestamp(eventTimestamp);
                            }
                            if (eventType.equals("review_loan_agreement_clicked") && v.getLoanAgreementStartTime() == null) {
                                v.setLoanAgreementStartTime(eventTimestamp);
                            }
                            if (eventType.equals("loan_agreement_info_continue")) {
                                v.setLoanAgreementEndTime(eventTimestamp);
                            }
                            HashSet<String> hs=v.getDocBanks();
                            if(eventType.contains("doc_bank_statement_uploaded_"))
                            {
                                hs.add(eventType);
                            }
                            v.setDocBanks(hs);

                            if(eventType.equals("verify_later_clicked"))
                            {
                                v.setVerifyLaterClicked(true);
                            }
                            HashSet<String> hs1=v.getIncomeVerificationOptions();
                            if(eventType.equals("verify_via_netbanking_screen_shown")||eventType.equals("verify_via_pdf_upload_continue")||eventType.equals("email_bank_statement_continue"))
                            {
                                hs1.add(eventType);
                            }
                            v.setIncomeVerificationOptions(hs1);


                            hm.put(userId, v);

                        } else {
                            Variables v = hm.get(userId);
                            HashSet<String> loanApps1 = new HashSet<>();

                            HashMap<Integer, HashSet<String>> tempMap = v.getSessionsForScreen();

                            for (Map.Entry<Integer, HashSet<String>> me : screens.entrySet()) {
                                int screenNum = me.getKey();
                                HashSet<String> screenEvents = me.getValue();
                                if (screenEvents.contains(eventType)) {
                                    HashSet<String> sessionIds = tempMap.get(screenNum);
                                    sessionIds.add(sessionId);
                                    tempMap.put(screenNum, sessionIds);
                                    break;
                                }
                            }
                            HashMap<Integer, Double> hashMap = v.getTimeSpentOnScreens();


                            for (Map.Entry<Integer, HashSet<String>> me : screens.entrySet()) {
                                int screenNum = me.getKey();
                                HashSet<String> screenEvents = me.getValue();
                                if (screenEvents.contains(eventType)) {
                                    Double initialVal = hashMap.get(screenNum);
                                    Double tempVal = ((sdf.parse(eventTimestamp).getTime()) - sdf.parse(v.getPrevTimestamp()).getTime()) / 60000.00;
                                    hashMap.put(screenNum, tempVal + initialVal);

                                    break;
                                }
                            }
                            v.setTimeSpentOnScreens(hashMap);

                            if (eventType.equals("review_nach_clicked") && v.getEnachStartTime() == null) {
                                v.setEnachStartTime(eventTimestamp);
                            }
                            if (eventType.equals("nach_agreement_share_clicked")) {
                                v.setEnachEndTime(eventTimestamp);
                            }

                            v.setSessionsForScreen(tempMap);

                            if (v.getSubmitFlag() == false) {

                                HashSet<String> sessionIds = v.getSessionIds();
                                sessionIds.add(sessionId);
                            }


                            if (loanAppsForUsers.containsKey(userId)) {
                                HashSet<String> loanApps = loanAppsForUsers.get(userId);
                                if (hasKey(object, "loan_application_no")) {
                                    String loanApplication = object.getString("loan_application_no");
                                    if (!loanApps.contains(loanApplication)) {
                                        loanApps.add(loanApplication);
                                        loanAppsForUsers.put(userId, loanApps);
                                        v.setCountOfLoanApps(v.getCountOfLoanApps() + 1);
                                    }
                                }
                            } else {
                                if (hasKey(object, "loan_application_no")) {
                                    loanApps1.add(object.getString("loan_application_no"));
                                    v.setCountOfLoanApps(1);
                                    loanAppsForUsers.put(userId, loanApps1);
                                }

                            }

                            if (hasKey(object, "salary")) {
                                if (v.getTempSalary() != null) {
                                    if (Float.parseFloat(object.getString("salary")) != (Float.parseFloat(v.getTempSalary()))) {
                                        v.setCountOfSalaryChanged(v.getCountOfSalaryChanged() + 1);
                                        v.setTempSalary(object.getString("salary"));
                                    }
                                } else {
                                    v.setCountOfSalaryChanged(0);
                                    v.setTempSalary(object.getString("salary"));

                                }
                                v.setRecentSalary(object.getString("salary"));

                            }
                            if (hasKey(object, "emp_type")) {
                                if (!object.getString("emp_type").equalsIgnoreCase("undefined") && !object.getString("emp_type").equalsIgnoreCase("")) {

                                    if (v.getTempEmpType() != null) {
                                        if (!object.getString("emp_type").equalsIgnoreCase(v.getTempEmpType())) {
                                            v.setCountOfEmpChanged(v.getCountOfEmpChanged() + 1);
                                            v.setTempEmpType(object.getString("emp_type"));
                                        }
                                    } else {
                                        v.setCountOfEmpChanged(0);
                                        v.setTempEmpType(object.getString("emp_type"));
                                        v.setInitialEmpType(v.getTempEmpType());
                                    }
                                    v.setRecentEmpType(object.getString("emp_type"));
                                }

                            }
                            if (hasKey(object, "pincode")) {
                                if (v.getTempPincode() != null) {
                                    if (object.getString("pincode") == v.getTempPincode()) {
                                        v.setCountOfPincodeChanged(v.getCountOfPincodeChanged() + 1);
                                        v.setTempPincode(object.getString("pincode"));
                                    }
                                } else {
                                    v.setCountOfPincodeChanged(0);
                                    v.setTempPincode(object.getString("pincode"));
                                }

                            }
                            if (hasKey(object, "is_company_name_listed")) {
                                v.setCompanyListed(object.getString("is_company_name_listed"));
                            }
                            if (hasKey(object, "attribution_id")) {
                                v.getAttributionIds().add(object.getString("attribution_id"));
                            }
                            if (hasKey(object, "dob")) {
                                if (v.getTempDob() != null) {
                                    if (object.getString("dob") == v.getTempDob()) {
                                        v.setCountOfDobChanged(v.getCountOfDobChanged() + 1);
                                        v.setTempDob(object.getString("dob"));
                                    }
                                } else {
                                    v.setCountOfDobChanged(0);
                                    v.setTempDob(object.getString("dob"));
                                }
                            }
                            if (hasKey(object, "nach_status")) {
                                v.setNachStatus(object.getString("nach_status"));

                            }
                            if (eventType.equals("pay_emi_via_debit_clicked")) {
                                v.setCountOfEmiDebit(v.getCountOfEmiNetbanking() + 1);
                            }

                            if (eventType.equals("forgot_pan_clicked")) {
                                v.setCountOfForgotPan(v.getCountOfForgotPan() + 1);
                            }
                            if (eventType.equals("loan_amount_changed")) {
                                v.setCountOfAmountChanged(v.getCountOfAmountChanged() + 1);
                                if (v.getPlanSelectorTimestamp() != null) {
                                    if (sdf.parse(eventTimestamp).compareTo(sdf.parse(v.getPlanSelectorTimestamp())) > 0) {
                                        v.setCountOfAmountAfterContinue(v.getCountOfAmountAfterContinue() + 1);
                                    }
                                }
                                if(hasKey(object,"amount"))
                                {
                                    if(v.getInitLoanAmt()==0.0)
                                    {
                                        v.setInitLoanAmt(Double.parseDouble(object.getString("amount")));
                                    }
                                    else
                                    {
                                        Double tempPercent=((Double.parseDouble(object.getString("amount"))-v.getInitLoanAmt())/v.getInitLoanAmt())*100;
                                        v.setLoanAmtPercentage(tempPercent);
                                    }
                                }
                            }
                            if (eventType.equals("loan_tenure_changed")) {
                                v.setCountOfTenureChanged(v.getCountOfTenureChanged() + 1);
                                if (v.getPlanSelectorTimestamp() != null) {
                                    if (sdf.parse(eventTimestamp).compareTo(sdf.parse(v.getPlanSelectorTimestamp())) > 0) {
                                        v.setCountOfTenureAfterContinue(v.getCountOfTenureAfterContinue() + 1);
                                    }
                                }
                                if(hasKey(object,"tenure"))
                                {
                                    if(v.getInitLoanTenure()==0.0)
                                    {
                                        v.setInitLoanTenure(Double.parseDouble(object.getString("tenure")));
                                    }
                                    else
                                    {
                                        Double tempPercent=Double.parseDouble(object.getString("tenure"))-v.getInitLoanTenure();
                                        v.setLoanTenurePercentage(tempPercent);

                                    }
                                }
                            }
                            if (eventType.equals("plan_selector_continue")) {
                                if (v.getPlanSelectorTimestamp() == null) {
                                    v.setPlanSelectorTimestamp(eventTimestamp);
                                    v.setCountOfAmountAfterContinue(0);
                                    v.setCountOfTenureAfterContinue(0);
                                }
                            }
                            if (eventType.equals("current_address_document_selected")) {
                                v.setCountOfCurrentAddressDoc(v.getCountOfCurrentAddressDoc() + 1);
                            }
                            if (eventType.equals("reject_age_restriction_screen_shown")) {
                                v.setCountOfAgeRestriction(v.getCountOfAgeRestriction() + 1);
                            }
                            if (eventType.equals("reject_invalid_bank_screen_shown")) {
                                v.setCountOfInvalidBank(v.getCountOfInvalidBank() + 1);
                            }
                            if (eventType.equals("reject_invalid_location_screen_shown")) {
                                v.setCountOfInvalidLocation(v.getCountOfInvalidLocation() + 1);
                            }
                            if (eventType.equals("reject_invalid_salary_mode_screen_shown")) {
                                v.setCountOfInvalidSalary(v.getCountOfInvalidSalary() + 1);
                            }
                            if (eventType.equals("reject_itr_screen_shown")) {
                                v.setCountOfItr(v.getCountOfItr() + 1);
                            }
                            if (eventType.equals("reject_loan_purpose_screen_shown")) {
                                v.setCountOfLoanPurpose(v.getCountOfLoanPurpose() + 1);
                            }
                            if (eventType.equals("reject_low_salary_screen_shown")) {
                                v.setCountOfLowSalary(v.getCountOfLowSalary() + 1);
                            }
                            if (eventType.equals("reject_no_netbanking_screen_shown")) {
                                v.setCountOfNoNetbanking(v.getCountOfNoNetbanking() + 1);
                            }
                            if(eventType.equals("reject_education_level_screen_shown"))
                            {
                                v.setCountOfEducationReject(v.getCountOfEducationReject());
                            }
                            if (eventType.equals("id_proof_type")) {
                                v.setIdType(object.getString("selected_id_proof"));
                            }
                            if (eventType.equals("doc_pan_uploaded")) {
                                v.setIdType("PAN");
                            }
                            if (eventType.equals("doc_aadhaar_front_uploaded")) {
                                v.setIdType("AADHAR");
                            }
                            if (eventType.equals("doc_aadhaar_back_uploaded")) {
                                v.setIdType("AADHAR");
                            }
                            if (eventType.equals("current_address_document_selected")) {
                                if (hasKey(object, "address_doc_type")) {
                                    v.setDocType(object.getString("address_doc_type"));
                                }
                            }
                            if (eventType.equals("resubmit_docs_clicked")) {
                                v.setResubmitDocs(true);
                            }
                            if (eventType.equals("upload_docs_incomplete_error_popup")) {
                                v.setCountOfUploadError(v.getCountOfUploadError() + 1);
                            }
                            if (eventType.equals("docs_imps_verify_retry_clicked")) {
                                v.setCountOfVerificationAttempts(v.getCountOfVerificationAttempts() + 1);
                            }
                            if (eventType.equals("docs_imps_verify_clicked")) {
                                v.setCountOfVerificationAttempts(v.getCountOfVerificationAttempts() + 1);
                            }
                            if (eventType.equals("verify_via_netbanking_screen_shown")) {
                                v.setIncomeVerification("Netbanking");
                            }
                            if (eventType.equals("verify_via_pdf_upload_continue")) {
                                v.setIncomeVerification("Pdf");
                            }
                            if (eventType.equals("email_bank_statement_continue")) {
                                v.setIncomeVerification("Email");
                            }
                            if (eventType.equals("loan_review_submit_clicked")) {
                                v.setSubmitFlag(true);
                                v.setSubmitTimestamp(eventTimestamp);
                            }
                            if (eventType.equals("pay_emi_via_netbanking_chosen")) {
                                v.setCountOfEmiNetbanking(v.getCountOfEmiNetbanking() + 1);
                            }
                            if (hasKey(object, "attribution_id") && v.getLoanOverviewTimestamp() != null) {
                                if (sdf.parse(eventTimestamp).compareTo(sdf.parse(v.getLoanOverviewTimestamp())) > 0) {
                                    HashSet<String> stringHashSet = v.getAttributionIdsAfterLoan();
                                    stringHashSet.add(object.getString("attribution_id"));
                                    v.setAttributionIdsAfterLoan(stringHashSet);
                                }
                            }
                            if (hasKey(object, "attribution_id") && v.getLoanOverviewTimestamp() != null) {
                                long day30 = 30l * 24 * 60 * 60 * 1000;
                                if (sdf.parse(eventTimestamp).after(new Date(sdf.parse(v.getLoanOverviewTimestamp()).getTime() + day30))) {
                                    HashSet<String> stringHashSet = v.getAttributionIdsAfterLoan1();
                                    stringHashSet.add(object.getString("attribution_id"));
                                    v.setAttributionIdsAfterLoan1(stringHashSet);
                                }
                            }
                            if (eventType.equals("review_loan_agreement_clicked") && v.getLoanAgreementStartTime() == null) {
                                v.setLoanAgreementStartTime(eventTimestamp);
                            }
                            if (eventType.equals("loan_agreement_info_continue")) {
                                v.setLoanAgreementEndTime(eventTimestamp);
                            }
                            if (eventType.equals("review_nach_clicked") && v.getCountEnach() == 0) {
                                v.setEnachStartTime(eventTimestamp);
                                v.setCountEnach(1);
                            }
                            if (eventType.equals("nach_agreement_share_clicked")) {
                                v.setEnachEndTime(eventTimestamp);
                            }
                            if (eventType.equals("loan_overview_screen_shown")) {
                                v.setLoanOverviewTimestamp(eventTimestamp);
                            }
                            if (eventType.equals("aadhaar_front_upload_tutorial_continue")) {
                                Double tempVal = (sdf.parse(eventTimestamp).getTime() - sdf.parse(v.getPrevTimestamp()).getTime()) / 60000.00;
                                if (tempVal > v.getTimeSpentOnAadharFront()) {
                                    v.setTimeSpentOnAadharFront(tempVal);
                                }
                            }
                            if (eventType.equals("aadhaar_upload_tutorial_continue")) {
                                Double tempVal = (sdf.parse(eventTimestamp).getTime() - sdf.parse(v.getPrevTimestamp()).getTime()) / 60000.00;
                                if (tempVal > v.getTimeSpentOnAadhar()) {
                                    v.setTimeSpentOnAadhar(tempVal);
                                }
                            }
                            if (eventType.equals("aadhaar_back_upload_tutorial_continue")) {
                                Double tempVal = (sdf.parse(eventTimestamp).getTime() - sdf.parse(v.getPrevTimestamp()).getTime()) / 60000.00;
                                if (tempVal > v.getTimeSpentOnAadharBack()) {
                                    v.setTimeSpentOnAadharBack(tempVal);
                                }
                            }
                            if (eventType.equals("ca_back_upload_tutorial_continue")) {
                                Double tempVal = (sdf.parse(eventTimestamp).getTime() - sdf.parse(v.getPrevTimestamp()).getTime()) / 60000.00;
                                if (tempVal > v.getTimeSpentOnCaBackUpload()) {
                                    v.setTimeSpentOnCaBackUpload(tempVal);
                                }
                            }
                            if (eventType.equals("ca_front_upload_tutorial_continue")) {
                                Double tempVal = (sdf.parse(eventTimestamp).getTime() - sdf.parse(v.getPrevTimestamp()).getTime()) / 60000.00;
                                if (tempVal > v.getTimeSpentOnCaFrontUpload()) {
                                    v.setTimeSpentOnCaFrontUpload(tempVal);
                                }
                            }
                            if (eventType.equals("cheque_upload_tutorial_continue")) {
                                Double tempVal = (sdf.parse(eventTimestamp).getTime() - sdf.parse(v.getPrevTimestamp()).getTime()) / 60000.00;
                                if (tempVal > v.getTimeSpentOnChequeUpload()) {
                                    v.setTimeSpentOnChequeUpload(tempVal);
                                }
                            }
                            if (eventType.equals("itr_upload_tutorial_continue")) {
                                Double tempVal = (sdf.parse(eventTimestamp).getTime() - sdf.parse(v.getPrevTimestamp()).getTime()) / 60000.00;
                                if (tempVal > v.getTimeSpentOnItrUpload()) {
                                    v.setTimeSpentOnItrUpload(tempVal);
                                }
                            }
                            if (eventType.equals("PAN_upload_tutorial_continue")) {
                                Double tempVal = (sdf.parse(eventTimestamp).getTime() - sdf.parse(v.getPrevTimestamp()).getTime()) / 60000.00;
                                if (tempVal > v.getTimeSpentOnPanUpload()) {
                                    v.setTimeSpentOnPanUpload(tempVal);
                                }
                            }
                            HashSet<String> hs=v.getDocBanks();
                            if(eventType.contains("doc_bank_statement_uploaded_"))
                            {
                                hs.add(eventType);
                            }
                            v.setDocBanks(hs);
                            if(eventType.equals("verify_later_clicked"))
                            {
                                v.setVerifyLaterClicked(true);
                            }
                            HashSet<String> hs1=v.getIncomeVerificationOptions();
                            if(eventType.equals("verify_via_netbanking_screen_shown")||eventType.equals("verify_via_pdf_upload_continue")||eventType.equals("email_bank_statement_continue"))
                            {
                                hs1.add(eventType);
                            }
                            v.setIncomeVerificationOptions(hs1);
                            v.setPrevTimestamp(eventTimestamp);

                            hm.put(userId, v);
                        }
                    }
                    }catch (Exception e) {
                    e.printStackTrace();
                }
            }



            for (Map.Entry<String, Variables> entry : hm.entrySet()) {
                Variables v = entry.getValue();

                ArrayList<String> arr = new ArrayList<>();
                arr.add(entry.getKey());
                arr.add(String.valueOf(v.getCountOfForgotPan()));
                arr.add(String.valueOf(v.getCountOfAmountChanged()));
                arr.add(String.valueOf(v.getCountOfTenureChanged()));
                arr.add(String.valueOf(v.getCountOfAmountAfterContinue()));
                arr.add(String.valueOf(v.getCountOfTenureAfterContinue()));
                arr.add(String.valueOf(v.getCountOfCurrentAddressDoc()));
                arr.add(String.valueOf(v.getCountOfAgeRestriction()));
                arr.add(String.valueOf(v.getCountOfInvalidBank()));
                arr.add(String.valueOf(v.getCountOfInvalidLocation()));
                arr.add(String.valueOf(v.getCountOfInvalidSalary()));
                arr.add(String.valueOf(v.getCountOfItr()));
                arr.add(String.valueOf(v.getCountOfLoanPurpose()));
                arr.add(String.valueOf(v.getCountOfLowSalary()));
                arr.add(String.valueOf(v.getCountOfNoNetbanking()));
                arr.add(String.valueOf(v.getCountOfEducationReject()));
                arr.add(v.getIdType());
                arr.add(v.getDocType());
                arr.add(String.valueOf(v.getCountOfLoanApps()));
                arr.add(String.valueOf(v.getCountOfSalaryChanged()));
                arr.add(String.valueOf(v.getCountOfEmpChanged()));
                try {
                    arr.add(String.valueOf(!(v.getInitialEmpType().equalsIgnoreCase(v.getTempEmpType()))));
                } catch (NullPointerException ne) {
                    arr.add(String.valueOf(false));
                }
                arr.add(String.valueOf(v.getResubmitDocs()));
                arr.add(String.valueOf(v.getAttributionIds().size()));
                arr.add(String.valueOf(v.getCountOfUploadError()));
                arr.add(String.valueOf(v.getCountOfPincodeChanged()));
                arr.add(v.getCompanyListed());
                arr.add(String.valueOf(v.getCountOfVerificationAttempts()));
                arr.add(String.valueOf(v.getCountOfDobChanged()));
                arr.add(String.valueOf(v.getIncomeVerification()));
                if (v.getSubmitFlag()) {
                    arr.add(String.valueOf(v.getSessionIds().size()));
                    Date firstDate = sdf.parse(v.getInitialTimestamp());
                    Date secondDate = sdf.parse(v.getSubmitTimestamp());
                    arr.add(String.valueOf((secondDate.getTime() - firstDate.getTime()) / 60000.00));
                } else {
                    arr.add("");
                    arr.add("");
                }
                TreeMap<Integer, HashSet<String>> sortedMap = new TreeMap<>(v.getSessionsForScreen());
                for (Map.Entry<Integer, HashSet<String>> me : sortedMap.entrySet()) {
                    arr.add(String.valueOf(me.getValue().size()));
                }
                TreeMap<Integer, Double> sortedMap1 = new TreeMap<>(v.getTimeSpentOnScreens());
                for (Map.Entry<Integer, Double> me : sortedMap1.entrySet()) {
                    arr.add(String.valueOf(me.getValue()));
                }
                arr.add(String.valueOf(v.getAttributionIdsAfterLoan().size()));
                arr.add(String.valueOf(v.getAttributionIdsAfterLoan1().size()));
                arr.add(v.getNachStatus());
                arr.add(String.valueOf(v.getCountOfEmiDebit()));
                arr.add(String.valueOf(v.getCountOfEmiNetbanking()));
                try{
                if (v.getLoanAgreementStartTime() != null && v.getLoanAgreementEndTime() != null) {
                    arr.add(String.valueOf((sdf.parse(v.getLoanAgreementEndTime()).getTime() - (sdf.parse(v.getLoanAgreementStartTime())).getTime()) / 60000.00));
                } else {
                    arr.add("");
                }
                }catch (Exception e) {
                    arr.add("");
                    e.printStackTrace();
                }
                try{
                if (v.getEnachStartTime() != null && v.getEnachEndTime() != null) {
                    arr.add(String.valueOf((sdf.parse(v.getEnachEndTime()).getTime() - (sdf.parse(v.getEnachStartTime())).getTime()) / 60000.00));
                } else {
                    arr.add("");
                }
                }catch (Exception e) {
                    arr.add("");
                    e.printStackTrace();
                }
                arr.add(String.valueOf(v.getTimeSpentOnAadharFront()));
                arr.add(String.valueOf(v.getTimeSpentOnAadhar()));
                arr.add(String.valueOf(v.getTimeSpentOnCaBackUpload()));
                arr.add(String.valueOf(v.getTimeSpentOnCaFrontUpload()));
                arr.add(String.valueOf(v.getTimeSpentOnChequeUpload()));
                arr.add(String.valueOf(v.getTimeSpentOnItrUpload()));
                arr.add(String.valueOf(v.getTimeSpentOnPanUpload()));
                arr.add(String.valueOf(v.getDocBanks().size()));
                arr.add(String.valueOf(v.getVerifyLaterClicked()));
                arr.add(String.valueOf(v.getIncomeVerificationOptions().size()));
                arr.add(String.valueOf(v.getRecentSalary()));
                arr.add(String.valueOf(v.getRecentEmpType()));
                arr.add(String.valueOf(v.getLoanAmtPercentage()));
                arr.add(String.valueOf(v.getLoanTenurePercentage()));

                String[] array = arr.toArray(new String[0]);
                writer.writeNext(array);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasKey(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key);
    }
}
