<template>
  <div>
    <basic-container>
      <p style="text-align: center;">
        <img src="https://img.shields.io/badge/Release-V2.7.0-green.svg" alt="Downloads"/>
        <img src="https://img.shields.io/badge/JDK-1.8+-green.svg" alt="Build Status"/>
        <img src="https://img.shields.io/badge/Spring%20Cloud-Hoxton.SR3-blue.svg" alt="Coverage Status"/>
        <img src="https://img.shields.io/badge/Spring%20Boot-2.2.6.RELEASE-blue.svg" alt="Downloads"/>
        <a target="_blank" href="https://www.sailmi.com">
          <img src="https://img.shields.io/badge/Saber%20Author-Small%20Chill-ff69b4.svg" alt="Downloads"/>
        </a>
        <a target="_blank" href="https://www.sailmi.com">
          <img src="https://img.shields.io/badge/Copyright%20-@sailmiX-%23ff3f59.svg" alt="Downloads"/>
        </a>
      </p>
    </basic-container>
    <el-col :span="16">
      <basic-container>
        <el-collapse v-model="activeNames" @change="handleChange">

          <el-collapse-item title="欢迎使用" name="5">
            欢迎使用运营管理系统
          </el-collapse-item>
        </el-collapse>
      </basic-container>
    </el-col>
    <el-col :span="8">
      <el-row>
        <basic-container>
          <div class="el-font-size">
            <span>产品名称</span>
            <el-divider direction="vertical"/>
            <span><el-tag>运营管理平台</el-tag></span>
            <el-divider content-position="right"><i class="el-icon-star-off"/></el-divider>

          </div>
        </basic-container>
      </el-row>
      <el-row>
        <basic-container>
          <el-collapse v-model="logActiveNames" @change="handleChange">
            版本说明
          </el-collapse>
        </basic-container>
      </el-row>
    </el-col>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";

  export default {
    name: "wel",
    data() {
      return {
        activeNames: ['1', '2', '3', '5'],
        logActiveNames: ['18']
      };
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    created() {
    },
    methods: {
      handleChange(val) {
		console.log(val);
      }
    }
  };
</script>

<style>
  .el-divider--horizontal {
    margin: 12px 0 !important;
  }

  .el-font-size {
    font-size: 14px;
  }
</style>

