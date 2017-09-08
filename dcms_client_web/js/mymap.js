/**
 * Created by Hardsun on 2017/6/22.
 */

function Mymap() {
    this.map = new Object();
    this.put = function (key, value) {
        var s = "this.map.key" + key + " = " + JSON.stringify(value) + ";"
        eval(s);
    };
    this.get = function (key) {
        var v = eval("this.map.key" + key + ";");
        return v;
    };
    this.remove = function (key) {
        eval("delete this.map.key" + key + ";");
    };
    this.keySet = function () {
        var keySets = new Array();
        for (key in this.map) {
            if (!(typeof(this.map[key]) == "function")) {
                keySets.push(key.slice(3));
            }
        }
        return keySets;
    };
    this.copy = function () {
        var copyMap = new Mymap();
        var keys = this.keySet();
        for (index in keys) {
            copyMap.put(keys[index], this.get(keys[index]));
        }
        return copyMap;
    };
}