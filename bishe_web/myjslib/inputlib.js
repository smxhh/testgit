function checkInput(inputname){
    if ($(inputname).val().length == 0 || $.trim($(inputname).val()) == "") {
        alert(inputname+"项资料为空。。，请填写");
        return -1;
    }
    return 0;
}