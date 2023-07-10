# Star Telescope 繁星望遠鏡

大學的繁星入學需要參照往年資料才能做出最佳的志願填選，但是無論是紙本手翻或是網頁點選都花費較多的時間，因此繁星望遠鏡是一個縮短加速這個過程的程式，能夠用較少的時間來得到更完整的資訊。

## 目錄

1. [功能](https://github.com/NatsuCamellia/Star#功能)
2. [版本與下載](https://github.com/NatsuCamellia/Star#版本與下載)
3. [操作說明](https://github.com/NatsuCamellia/Star#操作說明)
4. [註記](https://github.com/NatsuCamellia/Star#註記)
5. [Q&A](https://github.com/NatsuCamellia/Star#qa)


## 功能
* 查詢繁星結果
* 校系歷年各科門檻、比序結果
* 最愛清單儲存科系，可供下次開啟使用
* 多重檢視一次比較多個科系
* 五標篩選
* 自動比對是否符合標準

## 版本與下載

> 目前最新內容為 112 年放榜結果。

1. [前往版本頁面](https://github.com/NatsuCamellia/Star/releases)在 Assets 中下載 *.jar 檔案。
2. 【只需要做一次】版本 1.2.5 後，請下載並安裝 JDK 17，有很多網站可供下載，這裡提供 [Temurin]([https://adoptium.net/temurin/releases/](https://adoptium.net/temurin/releases/?version=17))，懶人載點 [Windows](https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.7%2B7/OpenJDK17U-jdk_x64_windows_hotspot_17.0.7_7.msi)，[macOS M1/M2]()
3. 安裝完成後即可雙擊開啟程式，如果詢問「您要如何開啟此檔案」，則選擇 OpenJDK 或其他你下載的 Java。

## 操作說明

### 單一檢視
先選擇大學，再選擇科系，右方就會顯示該校系的歷年紀錄。

### 多重檢視
點擊結果表格就能切換單一檢視與多重檢視，切換到最愛清單查看校系。

### 篩選小工具
選擇學測成績並「點擊啟用篩選」，即可篩除無法通過門檻的校系。

### 最愛清單
在總覽中選擇科系，並點擊「新增到最愛」即可加入最愛清單。

刪除時需選取最愛清單中的科系後，再點擊「從最愛刪除」才能刪除。

## 註記
* 大學名稱、校系名稱與代碼，皆以112年為準。
* 新出現或是更名的校系，會沒有歷年資料。
* 內容僅供參考，實際資料請以大學甄選入學委員會公告為準。
* 多重檢視中的招生人數是指當年度的「招生名額」，並非「錄取名額」。

## Q&A
- 為什麼部分結果會出現「無資料」？

  因為程式是用「112 年度的校系名稱」搜尋歷年校系，當歷年沒有完全相符的校系名稱時，便會出現「無資料」的情況。

- 為什麼沒有校排篩選的功能？

  考量到篩選可能會排除掉有機會的科系，因此不打算加入此功能。
