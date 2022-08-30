//
//  ContentView.swift
//  TestTotoIT
//
//  Created by Anucha Seetasung on 30/8/2565 BE.
//
import SwiftUI

struct ContentView: View {
    @ObservedObject var webViewModel = WebViewModel(url: "https://www.winmasters.gr")
        
        var body: some View {
            WebView(webViewModel: webViewModel)
            if webViewModel.isLoading {
                ProgressView()
                    .frame(height: 30)
            }
        }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
