//
//  WebView.swift
//  TestTotoIT
//
//  Created by Anucha Seetasung on 30/8/2565 BE.
//
import SwiftUI
import WebKit

struct WebView: UIViewRepresentable {
    @ObservedObject var webViewModel: WebViewModel
    
    func makeCoordinator() -> WebView.Coordinator {
        Coordinator(self, webViewModel)
    }
    
    func makeUIView(context: Context) -> WKWebView {
        guard let url = URL(string: self.webViewModel.url) else {
            return WKWebView()
        }
        
        let request = URLRequest(url: url)
        let webView = WKWebView()
        webView.navigationDelegate = context.coordinator
        webView.load(request)
        
        return webView
    }
    
    func updateUIView(_ uiView: WKWebView, context: Context) {}
}
