//
//  WebViewModel.swift
//  TestTotoIT
//
//  Created by Anucha Seetasung on 30/8/2565 BE.
//
import SwiftUI

class WebViewModel: ObservableObject {
    @Published var isLoading: Bool = false
    @Published var canGoBack: Bool = false
    @Published var shouldGoBack: Bool = false
    @Published var title: String = ""
    
    var url: String
    
    init(url: String) {
        self.url = url
    }
}
