<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>이용약관</title>
    <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="/resources/vendor/summernote/summernote-lite.min.js"></script>
    <script src="/resources/vendor/summernote/lang/summernote-ko-KR.min.js"></script>
    <link rel="stylesheet" href="/resources/vendor/summernote/summernote-lite.min.css">
</head>
<body>
<textarea id="summernote" name="editordata"></textarea>
<button id="andongBtn" >button</button>

<script>

    var index = 0;
    var image = null;

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
            console.log("클릭");
            var markupStr = $('#summernote').summernote('code');
            data.append("file", file);
            data.append("values", markupStr);
            $.ajax({
                data: data,
                type: "POST",
                url: "/store/andongStoryTest",
                 processData : false,
                 contentType : false,
                success: function (data) {
                    //항상 업로드된 파일의 url이 있어야 한다.
                    $(editor).summernote('insertImage', data.url);
                    console.log(data.url)
                    alert("SUCCESS")
                }, error : function (){
                    alert("ㅋㅋ")
                }
            });
        }

        $('#andongBtn').click(function() {

            data = new FormData();
            console.log("클릭");
            var markupStr = $('#summernote').summernote('code');
            data.append("file", image);
            data.append("values", markupStr);
            $.ajax({
                data: data,
                type: "POST",
                url: "/store/andongStorytext",
                processData : false,
                contentType : false,
                success: function (data) {
                    alert("SUCCESS")
                }, error : function (){
                    alert("ㅠㅠ")
                }
            });

        })
    })


</script>

</body>
</html>