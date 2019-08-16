var wx_data = null;

$(function () {
    $.ajax({
        url: window.location.protocol + "//" + window.location.host + '//jssdk',
        data: {
            url: window.location.href
        },
        cache: false,
        type: 'GET',
        success: function (data) {
            if (data != null) {
                data = JSON.parse(data);
                if (data.errcode != null && data.errcode === '0') {
                    wx_data = JSON.parse(data.result);
                    wx.config({
                        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                        appId: wx_data.appId, // 必填，公众号的唯一标识
                        timestamp: wx_data.timestamp, // 必填，生成签名的时间戳
                        nonceStr: wx_data.nonceStr, // 必填，生成签名的随机串
                        signature: wx_data.signature,// 必填，签名
                        jsApiList: ['getLocation', 'openLocation'] // 必填，需要使用的JS接口列表
                    });

                    wx.ready(function () {
                        wx.checkJsApi({
                            jsApiList: [
                                'getLocation'
                            ],

                            success: function (res) {
                                // alert(JSON.stringify(res));
                                // alert(JSON.stringify(res.checkResult.getLocation));
                                if (res.checkResult.getLocation === false) {
                                    alert('你的微信版本太低，不支持微信JS接口，请升级到最新的微信版本！');
                                }
                            }
                        });

                        wx.getLocation({
                            type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                            success: function (res) {
                                var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                                var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                                var speed = res.speed; // 速度，以米/每秒计
                                var accuracy = res.accuracy; // 位置精度

                                wx.openLocation({
                                    latitude: latitude, // 纬度，浮点数，范围为90 ~ -90
                                    longitude: longitude, // 经度，浮点数，范围为180 ~ -180。
                                    name: '测试下', // 位置名
                                    address: '测试地址', // 地址详情说明
                                    scale: 1, // 地图缩放级别,整形值,范围从1~28。默认为最大
                                    infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
                                });
                            }
                        });


                    });

                    wx.error(function (res) {
                        alert("接口调取失败")
                    });
                } else if (data != null) {
                    alert(JSON.stringify(data));
                }
            } else {
                alert("返回值为空");
            }
        },
        error: function (e) {
            alert('请求失败');
            console.log(e);
        }
    })
})