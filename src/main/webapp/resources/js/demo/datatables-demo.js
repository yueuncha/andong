// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').dataTable();
});


$('#userFilter').change(function(){
  let type = this.options[this.selectedIndex].value;
  let result = ajax("POST", "/admin/user/filter",{"type":1})
  //DataTable.Api();

  //let oTable = $('#dataTable').dataTable();


  /*
  * $(document).ready( function() {
     $('#example').dataTable( {
       "language": {
         "url": "http://www.sprymedia.co.uk/dataTables/lang.txt"
       }
     } );
   } );
  * */
  //oSetting
 //let oSetting = oTable._fnSettingsFromNode();

  //oTable._fnAddData(oSetting, result, result.length);

  let oTable = $('#dataTable').dataTable();

  $('#userData > tr').remove();

  let str = "";
  for (let i = 0; i < result.length; i++) {
    str += "<tr><td>"+result[i]['user_name']+"</td>" +
        "<td>"+result[i]['user_type']+"</td>" +
        "<td>"+result[i]['marketing_use']+"</td>" +
        "<td>"+result[i]['push_use']+"</td>" +
        "<td>"+result[i]['mb_sns']+"</td>" +
        "<td>"+result[i]['mb_email']+"</td>" +
        "<td>"+result[i]['mb_gender']+"</td>" +
        "<td>"+result[i]['mb_birth']+"</td>" +
        "<td>"+result[i]['mb_regdate']+"</td>" +
        "<td>"+result[i]['mb_local']+"</td>" +
        "<td>"+result[i]['mb_local2']+"</td>" +
        "<td>"+result[i]['mb_foreigner']+"</td>" +
        "</tr>"
  }

  $('#userData').append(str)
})

function fnInquiryOne(value){
  location.href = '/admin/inquiry/view?iq_idx='+value;
}

function fnReportOne(value){
  location.href = '/admin/report/view?report_idx='+value;
}


function fnInquiryWrite(){
  $('#inquiryForm').submit()
  alert("답변이 등록되었습니다.")
}

  $('#storeView').ready(function (){
    let store_type = $('input[name="store_type"]').val();
    let list_idx = 0;
    if(store_type == 'ko'){
      list_idx = $('input[name="list_idx"]').val();
    }

    fnLangChange(list_idx, store_type, 0)
  })

let state = 0;

function fnLangChange(idx, type, change){
  state = change

  if(state != 0){
    state = 0;
    $(".store").css("display", "none");
  }else{
    state = 1;
    $(".store").css("display", "none");
    $('.store_'+idx).css("display","block");
  }
}

function fnLoadCategory(value){
  let result = ajax("POST", "/admin/category/view" , {"ct_idx" : value});
  console.log(result)
  console.log(result['ct_ko_nm'])
}




