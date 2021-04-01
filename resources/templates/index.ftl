<#-- @ftlvariable name="data" type="blog.IndexData" -->
<#-- @ftlvariable name="user" type="blog.User" -->
<html>
<body>
<ul>
    <#list data.items as item>
        <li>${item}</li>
    </#list>

</ul>
<a>${user.name}</a>
<a>${name}</a>
</body>
</html>