# DialogLibrary
> 一个android方面,常规的弹出类库,完成以下功能
* 常规的Toast定制</br>
  ![](https://github.com/momodae/DialogLibrary/blob/master/image/image1.gif)
  </br>
* 对话框定制(主要为底部弹出定制)</br>
  ![](https://github.com/momodae/DialogLibrary/blob/master/image/image2.gif)
  </br>
* 加载框定制(仿微信类)</br>
  ![](https://github.com/momodae/DialogLibrary/blob/master/image/image3.gif)
  </br>
* PopupPanel(弹出面板)</br>
  ![](https://github.com/momodae/DialogLibrary/blob/master/image/image4.gif)
  </br>
* Crouton弹出</br>
  ![](https://github.com/momodae/DialogLibrary/blob/master/image/image5.gif)
  </br>


#### Sample[下载](https://github.com/momodae/DialogLibrary/blob/master/apk/sample.apk?raw=true)

#### Compile
> compile 'com.cz.dialog-library:library:1.0.0'
</br>

#### Style配置,在此基础上,可对所有功能进行单独定制,具体定制可参考Sample
```
 <!--以下为所有布局配置-->
    <style name="PromptStyle">
        <!--加载进度弹窗-->
        <item name="promptProgressLayout">@style/_ProgressLayoutStyle</item>
        <item name="promptProgressIndicator">@style/_ProgressIndicator</item>
        <item name="promptProgressText">@style/_PromptText</item>
        <!--定制toast-->
        <item name="promptToast">@style/_PromptToast</item>
        <!--设定自定义toast弹出方向-->
        <item name="promptToastGravity">@style/_PromptToastGravity</item>
        <!--刷新类消息层-->
        <item name="promptCrouton">@style/_PromptCrouton</item>
        <!--成功弹窗-->
        <item name="promptSuccessLayout">@style/_PromptSuccessLayout</item>
        <item name="promptSuccessTick">@style/_PromptSuccessTick</item>
        <item name="promptSuccessText">@style/_PromptSuccessText</item>
    </style>
```


####难点
* Crouton的弹出,以及Fragment依赖
* PopupPanel定制
* Style的全局配置统一


