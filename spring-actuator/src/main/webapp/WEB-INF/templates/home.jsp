<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Spring Boot Actuator</title>
</head>
<body>
<h3>List of Actuator Endpoints</h3>
<ul>
    <li><a href="${actuator}">Actuator Endpoint</a></li>
    <li><a href="${beans}">Beans Endpoint</a></li>
    <li><a href="${configprops}">Configuration Properties Endpoint</a></li>
    <li><a href="${env}">Environment Endpoint</a></li>
    <li><a href="${conditions}">Conditions Endpoint</a></li>
    <li><a href="${caches}">Caches Endpoint</a></li>
    <li><a href="${health}">Health Endpoint</a></li>
    <li><a href="${info}">Info Endpoint</a></li>
    <li><a href="${loggers}">Loggers Endpoint</a></li>
    <li><a href="${metrics}">Metrics Endpoint</a></li>
    <li><a href="${mappings}">Mappings Endpoint</a></li>
</ul>
</body>
</html>