/**
 * Created by ang on 2017/8/8.
 */

$(document).ready(function(){
    // alert("hello");
    // var dataStr;
    // $.post("/hello",
    //     {
    //         name:"Donald Duck",
    //         city:"Duckburg"
    //     },
    //     function(data,status){
    //         alert("Data: " + data + "\nStatus: " + status);
    //
    //         dataStr=data;
    //     });
});



function getData(data,url){

    var result;

    $.ajax({
        type: "post",
        url: url,
        data: data,
        async:false,
        contentType: "application/x-www-form-urlencoded;charset:utf-8",
        success: function (data) {
            result = data;
        }
    });
    alert(result);
    return result;
}
