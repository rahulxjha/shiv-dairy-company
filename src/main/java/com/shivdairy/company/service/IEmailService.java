package com.shivdairy.company.service;

import com.shivdairy.company.model.MilkDetails;

public interface IEmailService {
    void sendSupplierBill(MilkDetails milkDetails);
}
