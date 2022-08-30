//
//  ApiDataModel.swift
//  TestTotoIT
//
//  Created by Anucha Seetasung on 31/8/2565 BE.
//
import SwiftUI

class AppDataModel: Codable {
    let ErrorMessage: String
    let ErrorType: String
    let RequestId: String
    let TimeStamp: String
    
    init(
        ErrorMessage: String,
        ErrorType: String,
        RequestId: String,
        TimeStamp: String
    ) {
        self.ErrorMessage = ErrorMessage
        self.ErrorType = ErrorType
        self.RequestId = RequestId
        self.TimeStamp = TimeStamp
    }
}
