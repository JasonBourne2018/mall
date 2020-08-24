<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<div class="categoryMenu">
    <c:foreach items="${cs}" var="c">
        <div class="eachCategory" cid="${c.id}">
            <span class="glyphicon glyphicon-link"></span>
            <a href="forecategory?cid=${cid}">
                ${c.name}
            </a>
        </div>
    </c:foreach>
</div>
