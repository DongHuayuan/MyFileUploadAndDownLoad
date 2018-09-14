<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>上传文件</title>
</head>
<body>


<h1>${tip}</h1>
<hr>
<div style="margin: 50px"><img src="<c:url value="/uploadFile/${fileName}"/>" width="300px" height="200px"></div>
<form action="/upload?action=uploadFile" method="post" enctype="multipart/form-data">
    <input type="file" name="file" id="name" value="请选择文件">
    <button type="submit">点击上传</button>
</form>


<hr/>

<div style="width: 1200px;height: 450px; margin:10px auto;background-color: #00cddc">
    <c:forEach items="${files}" var="file">
        <div style="height: 224px;width: 330px;float: left;margin-left: 20px">
            <form  action="${pageContext.request.contextPath}/download?downFileName=${file}" method="post">
                <img id="img" src="<c:url value="/uploadFile/${file}"/>" width="300px" height="200px"
                     style="float: left;margin-left: 20px">
                <p><a href="javascript:void(0);"onclick="myupload()" name="doPost">${file}</a></p>
            </form>
        </div>
    </c:forEach>
</div>





<script>
    var doPosts = document.getElementsByName("doPost");
    for (var i=0;i<doPosts.length;i++){
        doPosts[i].onclick = function () {
            console.log("1111111111");
            this.parentNode.parentNode.submit();
            console.log("2222222222");
        }


    }
</script>

</body>
</html>
