var global = {
	ifmRefresh : function(ifm) {
		if (ifm) {
			$(ifm).attr("src", $(ifm).attr("src"));
		} else {
			// 子页面
			ifm = window.top.document.getElementById("mainIfm");
			Gdt.ifmRefresh(ifm);
		}
	},
	chart : {
		fhChart : function(p) {
			jQuery('#' + p.renderTo).highcharts({
				chart : {
					type : 'line'
				},
				title : {
					text : '负荷曲线',
					x : -20
				},
				xAxis : {
					categories : p.categories
				},
				yAxis : {
					title : {
						text : '负荷 (kW)'
					},
					plotLines : [ {
						value : 0,
						width : 1,
						color : '#808080'
					} ],
					plotBands : [ {
						from : 0,
						to : 0.6,
						color : '#55BF3B' // green
					}, {
						from : 0.6,
						to : 0.8,
						color : '#DDDF0D' // yellow
					}, {
						from : 0.8,
						to : 1,
						color : '#DF5353' // red
					} ]
				},
				plotOptions : {
					spline : {
						marker : {
							enabled : false
						}
					}
				},
				tooltip : {
					valueSuffix : 'kW'
				},
				legend : {
					align : 'center',
					verticalAlign : 'bottom',
					borderWidth : 0
				},
				series : p.series
			});
		}
	},
	map : function() {
		this.map = new Object();
		this.length = 0;

		this.size = function() {
			return this.length;
		};

		this.put = function(key, value) {

			if (!this.map['_' + key]) {
				++this.length;
			}

			this.map['_' + key] = value;

		};

		this.remove = function(key) {

			if (this.map['_' + key]) {

				--this.length;
				return delete this.map['_' + key];
			} else {
				return false;
			}
		};

		this.containsKey = function(key) {

			return this.map['_' + key] ? true : false;

		};

		this.get = function(key) {

			return this.map['_' + key] ? this.map['_' + key] : null;

		};

		this.inspect = function() {
			var str = '';

			for ( var each in this.map) {
				str += '\n' + each + '  Value:' + this.map[each];
			}

			return str;
		};

	},
	post : function(p) {
		var result = null;
		jQuery.ajax({
			type : 'POST',
			async : false,
			cache : false,
			url : p.url,
			data : p.data,
			success : function(rtn) {
				result = rtn;
			}
		});
		return result;
	}
};

// array求和方法
Array.prototype.sum = function() {
	return this.reduce(function(partial, value) {
		return partial + value
	}, 0)
};
// 扩展Date的format方法
Date.prototype.format = function(format) {
	if (!format)
		format = "yyyy-MM-dd HH:mm:ss";

	var o = {
		"M+" : this.getMonth() + 1, // 月
		"d+" : this.getDate(), // 天
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分钟
		"s+" : this.getSeconds(), // 秒
		"W+" : this.getWeek(), // 周
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
Date.prototype.getWeek = function() {
	var date = this;
	var date2 = new Date(date.getTime());
	date2.setMonth(0, 1);
	var day1 = date.getDay();
	if (day1 == 0)
		day1 = 7;
	var day2 = date2.getDay();
	if (day2 == 0)
		day2 = 7;
	d = Math.round((date.getTime() - date2.getTime() + (day2 - day1)
			* (24 * 60 * 60 * 1000)) / 86400000);
	return Math.ceil(d / 7) + 1;
};
/*
 * 功能:实现 javascript的日期计算功能. 参数:interval,字符串表达式，表示要添加的时间间隔. y
 * 参数:number,数值表达式，表示要添加的时间间隔的个数. 参数:date,时间对象. 返回:新的时间对象. example:
 * $.fn.DateTrans(); //当前时区时间 $.fn.DateTrans(number:5); //当前时区时间加上5天
 * $.fn.DateTrans(number:-5); //当前时区时间减去5天
 * $.fn.DateTrans(interval:'y',number:2); //当前时区时间减去加上2年
 * $.fn.DateTrans(number:-5); //当前时区时间减去5天 $.fn.DateTrans().format(" yyyy-MM-dd HH:mm:ss");
 * 
 */
$.fn.DateTrans = function(setting) {
	var defaults = {
		interval : null,
		number : 0,
		date : new Date()
	};
	setting = $.extend({}, defaults, setting);
	var trans = function() {
		switch (setting.interval) {
		case "y": {
			setting.date.setFullYear(setting.date.getFullYear()
					+ setting.number);
			return setting.date;
			break;
		}
		case "q": {
			setting.date.setMonth(setting.date.getMonth() + setting.number * 3);
			return setting.date;
			break;
		}
		case "m": {
			setting.date.setMonth(setting.date.getMonth() + setting.number);
			return setting.date;
			break;
		}
		case "w": {
			setting.date.setDate(setting.date.getDate() + setting.number * 7);
			return setting.date;
			break;
		}
		case "d": {
			setting.date.setDate(setting.date.getDate() + setting.number);
			return setting.date;
			break;
		}
		case "h": {
			setting.date.setHours(setting.date.getHours() + setting.number);
			return setting.setting.date;
			break;
		}
		case "m": {
			setting.date.setMinutes(setting.date.getMinutes() + setting.number);
			return setting.date;
			break;
		}
		case "s": {
			setting.date.setSeconds(setting.date.getSeconds() + setting.number);
			return setting.date;
			break;
		}
		default: {
			setting.date.setDate(setting.date.getDate() + setting.number);
			return setting.date;
			break;
		}
		}
	};
	return trans();
};
