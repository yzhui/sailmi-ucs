<template>
  <basic-container>
    <avue-crud :option="option"
               :table-loading="loading"
               :data="data"
               :page="page"
               :permission="permissionList"
               :before-open="beforeOpen"
               v-model="form"
               ref="crud"
               @row-update="rowUpdate"
               @row-save="rowSave"
               @row-del="rowDel"
               @search-change="searchChange"
               @search-reset="searchReset"
               @selection-change="selectionChange"
               @current-change="currentChange"
               @size-change="sizeChange"
               @on-load="onLoad">
      <template slot="menuLeft">
        <el-button type="danger"
                   size="small"
                   icon="el-icon-delete"
                   plain
                   v-if="permission.message_delete"
                   @click="handleDelete">删 除
        </el-button>
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
  import {getList, getDetail, add, update, remove} from "@/api/message/message";
  import {mapGetters} from "vuex";

  export default {
    data() {
      return {
        form: {},
        query: {},
        loading: true,
        page: {
          pageSize: 10,
          currentPage: 1,
          total: 0
        },
        selectionList: [],
        option: {
          height: 'auto',
          calcHeight: 210,
          searchShow: true,
          searchMenuSpan: 6,
          tip: false,
          border: true,
          index: true,
          viewBtn: true,
          selection: true,
          column: [
            {
              label: "短信id",
              prop: "id",
              rules: [{
                required: true,
                message: "请输入短信id",
                trigger: "blur"
              }]
            },
            {
              label: "手机号",
              prop: "mobile",
              rules: [{
                required: true,
                message: "请输入手机号",
                trigger: "blur"
              }]
            },
            {
              label: "短信参数",
              prop: "params",
              rules: [{
                required: true,
                message: "请输入短信参数",
                trigger: "blur"
              }]
            },
            {
              label: "短信系统生成的验证码",
              prop: "validateCode",
              rules: [{
                required: true,
                message: "请输入短信系统生成的验证码",
                trigger: "blur"
              }]
            },
            {
              label: "模板id",
              prop: "templateId",
              rules: [{
                required: true,
                message: "请输入模板id",
                trigger: "blur"
              }]
            },
            {
              label: "发送状态（0：失败，1：接口调用成功，2：发送成功）",
              prop: "sendStatus",
              rules: [{
                required: true,
                message: "请输入发送状态（0：失败，1：接口调用成功，2：发送成功）",
                trigger: "blur"
              }]
            },
            {
              label: "验证码验证状态（0：未验证，1：已验证）",
              prop: "validateStatus",
              rules: [{
                required: true,
                message: "请输入验证码验证状态（0：未验证，1：已验证）",
                trigger: "blur"
              }]
            },
            {
              label: "短信平台发送失败代码",
              prop: "failCode",
              rules: [{
                required: true,
                message: "请输入短信平台发送失败代码",
                trigger: "blur"
              }]
            },
            {
              label: "短信平台id",
              prop: "bizId",
              rules: [{
                required: true,
                message: "请输入短信平台id",
                trigger: "blur"
              }]
            },
            {
              label: "短信发送渠道",
              prop: "channel",
              rules: [{
                required: true,
                message: "请输入短信发送渠道",
                trigger: "blur"
              }]
            },
            {
              label: "重试次数",
              prop: "retry",
              rules: [{
                required: true,
                message: "请输入重试次数",
                trigger: "blur"
              }]
            },
            {
              label: "收到短信时间",
              prop: "receiveDate",
              rules: [{
                required: true,
                message: "请输入收到短信时间",
                trigger: "blur"
              }]
            },
            {
              label: "创建时间",
              prop: "createDate",
              rules: [{
                required: true,
                message: "请输入创建时间",
                trigger: "blur"
              }]
            },
            {
              label: "更新时间",
              prop: "updateDate",
              rules: [{
                required: true,
                message: "请输入更新时间",
                trigger: "blur"
              }]
            },
          ]
        },
        data: []
      };
    },
    computed: {
      ...mapGetters(["permission"]),
      permissionList() {
        return {
          addBtn: this.vaildData(this.permission.message_add, false),
          viewBtn: this.vaildData(this.permission.message_view, false),
          delBtn: this.vaildData(this.permission.message_delete, false),
          editBtn: this.vaildData(this.permission.message_edit, false)
        };
      },
      ids() {
        let ids = [];
        this.selectionList.forEach(ele => {
          ids.push(ele.id);
        });
        return ids.join(",");
      }
    },
    methods: {
      rowSave(row, done, loading) {
        add(row).then(() => {
          done();
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowUpdate(row, index, done, loading) {
        update(row).then(() => {
          done();
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowDel(row) {
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return remove(row.id);
          })
          .then(() => {
            this.onLoad(this.page);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
          });
      },
      handleDelete() {
        if (this.selectionList.length === 0) {
          this.$message.warning("请选择至少一条数据");
          return;
        }
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return remove(this.ids);
          })
          .then(() => {
            this.onLoad(this.page);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
            this.$refs.crud.toggleSelection();
          });
      },
      beforeOpen(done, type) {
        if (["edit", "view"].includes(type)) {
          getDetail(this.form.id).then(res => {
            this.form = res.data.data;
          });
        }
        done();
      },
      searchReset() {
        this.query = {};
        this.onLoad(this.page);
      },
      searchChange(params, done) {
        this.query = params;
        this.page.currentPage = 1;
        this.onLoad(this.page, params);
        done();
      },
      selectionChange(list) {
        this.selectionList = list;
      },
      selectionClear() {
        this.selectionList = [];
        this.$refs.crud.toggleSelection();
      },
      currentChange(currentPage){
        this.page.currentPage = currentPage;
      },
      sizeChange(pageSize){
        this.page.pageSize = pageSize;
      },
      onLoad(page, params = {}) {
        this.loading = true;
        getList(page.currentPage, page.pageSize, Object.assign(params, this.query)).then(res => {
          const data = res.data.data;
          this.page.total = data.total;
          this.data = data.records;
          this.loading = false;
          this.selectionClear();
        });
      }
    }
  };
</script>

<style>
</style>
