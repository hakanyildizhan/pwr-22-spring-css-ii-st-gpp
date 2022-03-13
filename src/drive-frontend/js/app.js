$(document).ready(()=>{
    let app = new Vue({
        el: '#app',
        data: 
        {
            name: "",
            path: "",
            type: "",
            createdAt: "",
            sharedWith: [],
            items: []
        },
        created: function () {
        },
        methods: {
            getItems : function() {
                $.ajax({
                    method: "GET",
                    url: AppConfig.URL+"/items/" + this.path,
                    cache: false,
                    success: (items) => {
                        this.items = items;
                    }
                });
            },
            getItemDetails : function() {
                $.ajax({
                    method: "GET",
                    url: AppConfig.URL+"/items/detail/" + this.path,
                    cache: false,
                    success: (item) => {
                        for (let prop in item){
                            this[prop] = item[prop];
                        }
                    }
                });
            },
            uploadItem : function() {
                var formData = new FormData();
                formData.append('file', $('#file')[0].files[0]);

                $.ajax({
                    method: "POST",
                    url: AppConfig.URL+"/items",
                    contentType: false,
                    data : formData,
                    success: (status) => {
                        console.log(status);
                        this.getItems();
                    }
                });
            },
            deleteItem : function(){
                $.ajax({
                    method: "DELETE",
                    url: AppConfig.URL+"/items/" + this.path,
                    success: (status) => {
                        this.getItems();
                    }
                });
            },
            downloadItem : function() {
                $.ajax({
                    method: "GET",
                    url: AppConfig.URL+"/items/download/" + this.path,
                    xhr: function () {
                        var xhr = new XMLHttpRequest();
                        xhr.onreadystatechange = function () {
                            if (xhr.readyState == 2) {
                                if (xhr.status == 200) {
                                    xhr.responseType = "blob";
                                } else {
                                    xhr.responseType = "text";
                                }
                            }
                        };
                        return xhr;
                    },
                    success: function (data) {
                        //Convert the Byte Data to BLOB object.
                        var blob = new Blob([data], { type: "application/octetstream" });
    
                        //Check the Browser type and download the File.
                        var isIE = false || !!document.documentMode;
                        if (isIE) {
                            window.navigator.msSaveBlob(blob, fileName);
                        } else {
                            var url = window.URL || window.webkitURL;
                            link = url.createObjectURL(blob);
                            var a = $("<a />");
                            a.attr("download", fileName);
                            a.attr("href", link);
                            $("body").append(a);
                            a[0].click();
                            $("body").remove(a);
                        }
                    }
                })
            },
            shareItem : function(email){
                $.ajax({
                    method: "POST",
                    url: AppConfig.URL+"/items/share",
                    data: this.convertItemDataToJson(this.path, email),
                    success: (status) => {
                        this.getItems();
                    }
                });
            },
            convertItemDataToJson : function(path, email){
                return JSON.stringify({
                    filePath: path,
                    email: email
                })
              }
        }
    });
});
