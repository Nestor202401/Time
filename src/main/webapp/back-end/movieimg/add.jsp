<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Vue File Agent Example</title>
  <!-- 引入 Vue.js -->
  <script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
  <!-- 引入 Vue File Agent CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/vue-file-agent@latest/dist/vue-file-agent.css" />

  <style>
    /* 自訂樣式 */
    #app {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
      border: 1px solid #ccc;
      border-radius: 10px;
      background-color: #f9f9f9;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .upload-btn {
      background-color: #4CAF50;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      margin-top: 20px;
    }

    .upload-btn:disabled {
      background-color: #aaa;
      cursor: not-allowed;
    }

    .input-field {
      margin-bottom: 20px;
    }

    .row {
      display: flex;
      flex-wrap: wrap;
      margin-bottom: 10px;
    }

    .col-sm-6 {
      flex: 1;
      min-width: 200px;
      padding: 0 10px;
    }

    .col-sm-12 {
      flex: 1 100%;
      padding: 0 10px;
    }

    label {
      display: block;
      margin-bottom: 5px;
    }

    input, textarea, select {
      width: 100%;
      padding: 8px;
      margin-bottom: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }

    .error-messages {
      color: red;
      margin-bottom: 20px;
    }
  </style>
</head>
<body>
  <div id="app">
    <!-- 錯誤訊息顯示區域 -->
    <div v-if="Object.keys(errorMessages).length > 0" class="error-messages">
      <div v-for="(message, field) in errorMessages" :key="field">
        <p>{{ field }}: {{ message }}</p>
      </div>
    </div>

    <!-- 电影ID输入字段 -->
    <div class="row">
      <div class="col-sm-12">
        <label for="movieName">電影名稱:</label>
        <input type="text" v-model="movieName"/>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-6">
        <label for="director">導演:</label>
        <input type="text" v-model="director"/>
      </div>
      
      <div class="col-sm-6">
        <label for="actor">演員:</label>
        <input type="text" v-model="actor"/>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-6">
        <label for="runtime">片長:</label>
        <input type="text" v-model="runtime"/>
      </div>
      <div class="col-sm-6">
        <label for="rating">電影等級:</label>
        <select v-model="movieRating" class="form-control">
          <option value="" selected>請選擇</option>
          <option value="1">普遍級</option>
          <option value="2">保護級</option>
          <option value="3">輔導級</option>
          <option value="4">限制級</option>
        </select>
      </div>
    </div>
    
    <div class="row">
      <div class="col-sm-6">
        <label for="releaseDate">上映時間:</label>
        <input type="date" v-model="releaseDate">
      </div>
      
      <div class="col-sm-6">
        <label for="endDate">下映時間:</label>
        <input type="date" v-model="endDate">
      </div>
    </div>
    
    <div class="row">
      <div class="col-sm-12">
        <label for="introduction">簡介:</label>
        <textarea v-model="introduction" rows="5"></textarea>
      </div>
    </div>
    
    <!-- Vue File Agent 元件 -->
    <vue-file-agent ref="vueFileAgent" :theme="'list'" :multiple="true" :deletable="true" :meta="true"
      :accept="'image/*,.zip'" :maxSize="'10MB'" :maxFiles="14" :helpText="'選擇圖片或 ZIP 檔案'"
      :errorText="{
      type: '無效的檔案類型。僅允許圖片或 ZIP 檔案',
      size: '檔案大小不得超過 10MB',
    }" @select="filesSelected($event)" @beforedelete="onBeforeDelete($event)" @delete="fileDeleted($event)"
      v-model="fileRecords"></vue-file-agent>
    
    <!-- 圖片簡介名稱輸入字段 -->
    <div v-for="(fileRecord, index) in fileRecordsForUpload" :key="index" class="input-field">
      <label :for="'customName' + index">圖片簡介名稱:</label>
      <input type="text" v-model="fileRecord.customName" :id="'customName' + index" placeholder="輸入圖片簡介名稱"/>
    </div>

    <!-- 上傳按鈕 -->
    <button class="upload-btn" :disabled="!fileRecordsForUpload.length || !movieName || !allNamesProvided"
      @click="uploadFiles()">上傳 {{ fileRecordsForUpload.length }} 檔案</button>
  </div>

  <!-- Vue File Agent 元件 Script -->
  <script src="https://cdn.jsdelivr.net/npm/vue-file-agent@latest/dist/vue-file-agent.umd.js"></script>

  <script>
    new Vue({
      el: '#app',
      data() {
        return {
          movieName: '',
          director: '',
          actor: '',
          runtime: '',
          movieRating: '',
          releaseDate: '',
          endDate: '',
          introduction: '',
          fileRecords: [],
          uploadUrl: 'movieimg.do?action=insertWithImages',
          uploadHeaders: { 'X-Test-Header': 'vue-file-agent' },
          fileRecordsForUpload: [],
          maxUploads: 14,
          errorMessages: {} // 用於存儲錯誤訊息
        };
      },
      computed: {
        allNamesProvided() {
          return this.fileRecordsForUpload.every(fileRecord => fileRecord.customName && fileRecord.customName.trim() !== '');
        }
      },
      methods: {
        uploadFiles() {
          if (this.fileRecordsForUpload.length > this.maxUploads) {
            alert('最多只能選擇14張圖片。');
            return;
          }

          var formData = new FormData();
          formData.append('movieName', this.movieName);
          formData.append('director', this.director);
          formData.append('actor', this.actor);
          formData.append('runtime', this.runtime);
          formData.append('movieRating', this.movieRating);
          formData.append('releaseDate', this.releaseDate);
          formData.append('endDate', this.endDate);
          formData.append('introduction', this.introduction);

          this.fileRecordsForUpload.forEach((fileRecord, index) => {
            formData.append('file' + index, fileRecord.file);
            formData.append('name' + index, fileRecord.customName);
          });

          var xhr = new XMLHttpRequest();
          xhr.open('POST', this.uploadUrl, true);
          xhr.setRequestHeader('X-Test-Header', 'vue-file-agent');

          xhr.onload = function () {
            if (xhr.status === 200) {
              this.fileRecordsForUpload = [];
              window.location.href = '../movietime/Success.html';
            } else {
              var response = JSON.parse(xhr.responseText);
              this.errorMessages = response.errorMsgs || {};
              alert('上傳失敗: ' + JSON.stringify(this.errorMessages));
            }
          }.bind(this);

          xhr.send(formData);
        },
        deleteUploadedFile(fileRecord) {
          this.$refs.vueFileAgent.deleteUpload(this.uploadUrl, this.uploadHeaders, fileRecord);
        },
        filesSelected(fileRecordsNewlySelected) {
          var validFileRecords = fileRecordsNewlySelected.filter((fileRecord) => !fileRecord.error);
          validFileRecords.forEach(fileRecord => {
            this.$set(fileRecord, 'customName', '');
          });
          this.fileRecordsForUpload = this.fileRecordsForUpload.concat(validFileRecords);
        },
        onBeforeDelete(fileRecord) {
          var i = this.fileRecordsForUpload.indexOf(fileRecord);
          if (i !== -1) {
            this.fileRecordsForUpload.splice(i, 1);
            var k = this.fileRecords.indexOf(fileRecord);
            if (k !== -1) this.fileRecords.splice(k, 1);
          } else {
            if (confirm('確定要刪除嗎？')) {
              this.$refs.vueFileAgent.deleteFileRecord(fileRecord);
            }
          }
        },
        fileDeleted(fileRecord) {
          var i = this.fileRecordsForUpload.indexOf(fileRecord);
          if (i !== -1) {
            this.fileRecordsForUpload.splice(i, 1);
          } else {
            this.deleteUploadedFile(fileRecord);
          }
        },
      },
    });
  </script>
</body>
</html>
