# 戰艦棋
Battle ship 一款有包含聊天室的 一對一連線(tcp) Java Game  (程式碼約 1500行)
![alt tag](https://raw.githubusercontent.com/bear1110/Java-SocketProgram/master/戰艦棋遊玩模式.PNG)
## Getting Started

下載下來之後 把它include 進 eclipse 的 專案當中 (選擇 Import existing Eclipse projects)
之後執行  src/view/connectGUI.java 即可執行

## 架構

![alt tag](https://raw.githubusercontent.com/bear1110/Java-SocketProgram/master/UML.png)

## 遊戲流程

一位玩家建立房間
另一位玩家 輸入對方的 Ip 位置 來建立連線
並且擺放船隻船隻

可使用方向鍵左右來改變船的擺放方式


## 遊戲進行中

只要點擊上方格子來猜對方的船在那兒

若猜中可繼續擊船

直到把對方玩家的船全部擊沉及算獲勝
