//获取地址栏信息
export function getSearchString(key) {
    // 获取URL中?之后的字符
    var addrstr = location.href;
    var str = addrstr.substring(addrstr.lastIndexOf("?") + 1, addrstr.length); //shopcode=125806649&branchcode=001&recommendid=6&openidfrom=&awardto=1001

    // 以&分隔字符串，获得类似name=xiaoli这样的元素数组
    var arr = str.split("&");
    var obj = new Object();

    // 将每一个数组元素以=分隔并赋给obj对象
    for (var i = 0; i < arr.length; i++) {
        var tmp_arr = arr[i].split("=");
        obj[decodeURIComponent(tmp_arr[0])] = decodeURIComponent(tmp_arr[1]);
    }
    return obj[key] || '';
}