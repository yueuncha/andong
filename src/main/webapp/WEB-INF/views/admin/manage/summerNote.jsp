<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="/resources/vendor/summernote/lang/summernote-ko-KR.min.js"></script>
<textarea id="summernote" name="editordata"></textarea>

<script>

    let index = 0;
    let image = null;
    let data = null;

    $(document).ready(function(){
        $('#summernote').summernote({
            height: 300,
            minHeight: null,             // set minimum height of editor
            maxHeight: null,             // set maximum height of editor
            focus: true,
            lang: "ko-KR"
            ,callbacks: {	//여기 부분이 이미지를 첨부하는 부분
            onImageUpload : function(files) {
                uploadSummernoteImageFile(files[0],this);
                index++;
                if(index == 1){
                    image = files[0]
                }
            },
            onPaste: function (e) {
                var clipboardData = e.originalEvent.clipboardData;
                if (clipboardData && clipboardData.items && clipboardData.items.length) {
                    var item = clipboardData.items[0];
                    if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
                         e.preventDefault();
                        }
                    }
                }
            }
        });

        function uploadSummernoteImageFile(file, editor) {
            data = new FormData();
            let markupStr = $('#summernote').summernote('code');
            data.append("file", file);
            data.append("values", markupStr);
            $.ajax({
                data : data,
                type : "POST",
                url : "/store/andongStoryTest",
                 processData : false,
                 contentType : false,
                success: function (data) {
                    //항상 업로드된 파일의 url이 있어야 한다.
                    console.log(data.url)
                    $(editor).summernote('insertImage', data.url);
                    $('input[name="tour_contents"]').val(data.url);
                    console.log(data.url)
                }, error : function (){
                }
            });
        }
        $('#andongBtn').click(function() {
            data = new FormData();
            let markupStr = $('#summernote').summernote('code');
            console.log(markupStr)
            let text123 = $('#storyWriteForm').val()
            console.log(text123)
            let json1 = $('input[name="tour_title"]').val();
            let json2 = $('input[name="tour_subtitle"]').val();
            let json3 = $('input[name="tour_category"]').val();
            let json4 = $('input[name="tour_contents"]').val();
            console.log(json1, json2, json3)
            //json.append("tour_contents", markupStr);
            //data.append("file", image);
            //data.append("values", json);

            let json = {
                "tour_title" : json1
                , "tour_subtitle" : json2
                , "tour_category" : json3
                , "tour_contents" : json4
            }

            $.ajax({
                type: "POST",
                url: "/store/andongStorytext",
                data : JSON.stringify(json),
                processData : false,
                contentType : "application/json",
                success: function (values) {
                    alert("게시글이 작성되었습니다.")
                    history.back();
                }, error : function (){

                }
            });

        })

    })

    $('#andongBtn').click(function() {
        data = new FormData();
        let markupStr = $('#summernote').summernote('code');
        console.log(markupStr)
        let text123 = $('#storyWriteForm').val()
        console.log(text123)
        let json1 = $('input[name="tour_title"]').val();
        let json2 = $('input[name="tour_subtitle"]').val();
        let json3 = $('input[name="tour_category"]').val();
        console.log(json1, json2, json3)
        //json.append("tour_contents", markupStr);
        data.append("file", image);
        //data.append("values", json);
        $.ajax({
            type: "POST",
            url: "/store/andongStorytext",
            data : data,
            processData : false,
            contentType : false,
            success: function (values) {
                alert("게시글이 작성되었습니다.")
                history.back();
            }, error : function (){

            }
        });

    })


</script>