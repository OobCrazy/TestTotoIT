//
//  ContentView.swift
//  TestTotoIT
//
//  Created by Anucha Seetasung on 30/8/2565 BE.
//
import SwiftUI

struct ContentView: View {
    @ObservedObject var webViewModel = WebViewModel(url: "https://www.winmasters.gr")
    @State private var dragAmount: CGPoint?
    let mainColor = Color(red: 219/255, green: 6/255, blue: 13/255)
        
    var body: some View {
        NavigationView {
            ZStack {
                WebView(webViewModel: webViewModel)
                if webViewModel.isLoading {
                    ProgressView()
                        .frame(height: 30)
                }
                
                GeometryReader { gp in // just to center initial position
                    ZStack {
                        NavigationLink(destination: LoadApiView(apiData: AppDataModel(ErrorMessage: "", ErrorType: "", RequestId: "", TimeStamp: ""))) {
                            Image(systemName: "plus")
                                .resizable()
                                .padding(10)
                                .background(mainColor)
                                .clipShape(Circle())
                                .foregroundColor(.white)
                                .frame(width: 50, height: 50, alignment: .center)
                        }
                        .animation(.none, value: dragAmount)
                        .position(self.dragAmount ?? CGPoint(x: (gp.size.width - 35), y: (gp.size.height / 2)))
                        .highPriorityGesture(  // << to do no action on drag !!
                            DragGesture()
                                .onChanged { self.dragAmount = $0.location})
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity) // full space
                }
            }
            .navigationBarHidden(true)
            .navigationBarTitle(Text("Home"))
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
