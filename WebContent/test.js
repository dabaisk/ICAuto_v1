Password:{},ImgCode:{}},UserName:"",Password:"",ImgCode:"",imgSrc:"",IcodeShow:!1,keyupHandler:function(){}}},created:function(){var t=this;this.keyupHandler=function(e){13===e.keyCode&&(t.$va.refreshAllValue(),t.login())},this.addKeyUp()},
beforeDestroy:function(){this.delKeyUp()},methods:{delKeyUp:function(){document.removeEventListener("keyup",this.keyupHandler)},addKeyUp:function(){document.addEventListener("keyup",this.keyupHandler)},login:function(){var t=this;this.$va.refreshAllValue(),this.UserName=this.$va.forms.UserName.value,
this.Password=this.$va.forms.Password.value;var e=this.$va.checkAll();if(e)return void layer.msgWarn(e);this.delKeyUp();var a=this,
s={Action:"Login",UserName:this.UserName,Password:this.Password};this.IcodeShow&&(s.ImgCode=this.ImgCode);
