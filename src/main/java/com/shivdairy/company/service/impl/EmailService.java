package com.shivdairy.company.service.impl;

import com.shivdairy.company.model.MilkDetails;
import com.shivdairy.company.service.IEmailService;
import com.shivdairy.company.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class EmailService implements IEmailService {
    @Value("${email.from}")
    private String systemEmail;

    @Autowired
    private EmailUtil emailUtil;

    public void sendSupplierBill(MilkDetails milkDetails) {
        String subject = "Milk payment details";
        String htmlBody = "<html>"
                + "<head></head>"
                + "<body>"
                + "<p>Hello " + milkDetails.getName() + ",</p>"
                + "<p>Please find attach document below to see your milk details including your total payment which " +
                "is being processed.</p>"
                + "<p>Regards, <br>Shiv Dairy Communications</p>";
        List<String> primaryRecipientList = new ArrayList<>();
        primaryRecipientList.add(milkDetails.getSupplier().getEmail());

        log.debug("{} is sending sendSupplierBill to-{} with subject-{}", systemEmail, (primaryRecipientList != null ?
                        primaryRecipientList.toString() : ""), subject);

        emailUtil.sendEmailMessage(primaryRecipientList, new ArrayList<>(), new ArrayList<>(), systemEmail, htmlBody,
                "", subject, new ArrayList<>(), false);
    }
}
