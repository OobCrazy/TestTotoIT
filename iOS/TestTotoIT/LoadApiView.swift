//
//  LoadApiView.swift
//  TestTotoIT
//
//  Created by Anucha Seetasung on 30/8/2565 BE.
//
import SwiftUI

struct LoadApiView: View {
    @State var apiData: AppDataModel
    @State private var isLoading = true
        
    var body: some View {
        if(isLoading) {
            HStack(spacing: 15) {
                ProgressView()
                Text("Loading…")
            }.onAppear(perform: loadApi)
        } else {
            ScrollView {
                VStack(alignment: .leading) {
                    Text("ErrorMessage: "+apiData.ErrorMessage).padding()
                    Text("ErrorType: "+apiData.ErrorType).padding()
                    Text("RequestId: "+apiData.RequestId).padding()
                    Text("TimeStamp: "+apiData.TimeStamp).padding()
                }
            }
            .navigationBarItems(trailing: Button(action: loadApi) {
                Text("Refresh")
            })
            .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
        }
    }
    
    func loadApi() {
        Task {
            let (data, _) = try await URLSession.shared.data(from: URL(string:"https://gamelaunch-stage.everymatrix.com/Loader/Start/2237/wolfgold?casinolobbyurl=https%3A%2F%2Fgames.staging.irl.aws.tipicodev.de%2Fen%2F&funMode=False&language=en&launchApi=true&_sid64=R3fJpfl4GwmcsDzrNpvOyS1YEGM4agCkwxB.kjbzBzZZkbyfU0_GGg")!)
            apiData = (try? JSONDecoder().decode(AppDataModel.self, from: data)) ?? AppDataModel(ErrorMessage: "Error, cannot load api data.", ErrorType: "", RequestId: "", TimeStamp: "")
            isLoading = false
        }
    }
}

struct LoadApiView_Previews: PreviewProvider {
    static var previews: some View {
        LoadApiView(apiData: AppDataModel(ErrorMessage: "", ErrorType: "", RequestId: "", TimeStamp: ""))
    }
}
