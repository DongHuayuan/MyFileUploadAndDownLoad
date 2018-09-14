<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>上传文件</title>
</head>
<body>


<h1>${tip}</h1>
<hr>
<%--<div style="margin: 50px"><img src="<c:url value="/uploadFile/${fileName}"/>" width="300px" height="200px"></div>--%>
<div style="margin-left: 150px;">
<form action="${pageContext.request.contextPath}/upload?action=uploadFile" method="post" enctype="multipart/form-data">
    <input type="file" name="file" id="name" value="请选择文件">
    <button type="submit">点击上传</button>
</form>
</div>


<hr/>

<div style="width: 1300px; margin:10px auto;background-color: #00cddc">
    <c:forEach items="${files}" var="file">
        <div style="height: 300px;width: 300px; float: left;margin-left: 20px;margin-top:20px;background-color: lightgrey;">
        <form  action="${pageContext.request.contextPath}/download?filename=${file}" method="post">
            <img id="img" src="<c:url value="${url}${file}"/>" style="width:300px; height:230px;margin-top: 20px">
            <p style="line-height: 30px;text-align: center">
                <a href="javascript:void(0);"onclick="myupload()" name="doPost">${file}</a>
                <a href="${pageContext.request.contextPath}/delete?fileName=${url}${file}">删除</a>
            </p>
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
