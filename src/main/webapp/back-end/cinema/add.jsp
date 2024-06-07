<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Generate Seat Map</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f9;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    .form-container99 {
        background-color: #ffffff;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        max-width: 400px;
        width: 100%;
    }
    .form-container99 h2 {
        margin-bottom: 20px;
        color: #333333;
    }
    .form-container99 label {
        display: block;
        margin-bottom: 8px;
        color: #555555;
    }
    .form-container99 input {
        width: calc(100% - 20px);
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #dddddd;
        border-radius: 5px;
    }
    .form-container99 button {
        width: 100%;
        padding: 10px;
        background-color: #007bff;
        border: none;
        border-radius: 5px;
        color: white;
        font-size: 16px;
        cursor: pointer;
    }
    .form-container99 button:hover {
        background-color: #0056b3;
    }
</style>
</head>
<body>
<div class="form-container99">
    <h2>創建座位表模板</h2>
    <form action="cinema.do" method="post">
        <input type="hidden" name="action" value="generate_seat_map">
        <label for="cinemaName">影廳名稱:</label>
        <input type="text" id="cinemaName" name="cinemaName" required>
        <label for="rows">列數:</label>
        <input type="number" id="rows" name="rows" required>
        <label for="columns">行數:</label>
        <input type="number" id="columns" name="columns" required>
        <button type="submit">創建</button>
    </form>
</div>
</body>
</html>
