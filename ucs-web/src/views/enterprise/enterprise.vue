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
                   v-if="permission.enterprise_delete"
                   @click="handleDelete">删 除
        </el-button>
      </template>
      <template slot-scope="scope" slot="menu">
        <el-button
          type="text"
          icon="el-icon-circle-plus-outline"
          size="small"
          @click.stop="handleService(scope.row,scope.index)"
        >开通服务
        </el-button>
      </template>
      <template slot-scope="{row}"
                slot="source">
        <div style="text-align:center">
          <i :class="row.source"></i>
        </div>
      </template>  
    </avue-crud>
    <el-dialog title="服务包配置"
               :visible.sync="box"
               width="20%">
      <el-tree :data="list"
               show-checkbox
               node-key="id"
               ref="tree"
               :default-checked-keys="defaultObj"
               :props="props">
      </el-tree>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="box = false">取 消</el-button>
        <el-button type="primary"
                   @click="submitService">确 定</el-button>
      </span>
    </el-dialog>
  </basic-container>
</template>

<script>
  import {getList, getDetail, add, update, remove,getEnterpriseService,grantService} from "@/api/enterprise/enterprise";
  import {grantServiceTree} from "@/api/system/service";
  import {mapGetters} from "vuex";

  export default {
    data() {
      return {
        rowid: "",	 
        form: {},
        box: false,
        props: {
          label: "serviceName",
          value: "id"
        },		
        query: {},
        list: [],		
        defaultObj: [],	
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
          index: false,
          viewBtn: true,
          selection: true,
          column: [
            {
              label: "企业ID",
              prop: "id",
              rules: [{
                required: true,
                message: "请输入企业ID",
                trigger: "blur"
              }]
            },
            {
              label: "企业名称",
              prop: "enterpriseName",
              rules: [{
                required: true,
                message: "请输入企业名称",
                trigger: "blur"
              }]
            },
            {
              label: "统一社会信用代码",
              prop: "creditCode",
              rules: [{
                required: true,
                message: "请输入统一社会信用代码",
                trigger: "blur"
              }]
            },
            {
              label: "行业类型",
              prop: "industryType",
              rules: [{
                required: true,
                message: "请输入行业类型",
                trigger: "blur"
              }]
            },
            {
              label: "企业类型",
              prop: "enterpriseType",
              rules: [{
                required: true,
                message: "请输入企业类型",
                trigger: "blur"
              }]
            },
            {
              label: "所在区域",
              prop: "region",
              rules: [{
                required: true,
                message: "请输入所在区域",
                trigger: "blur"
              }]
            },
            {
              label: "企业规模",
              prop: "enterpriseSize",
              rules: [{
                required: true,
                message: "请输入企业规模",
                trigger: "blur"
              }]
            },
            {
              label: "负责人",
              prop: "chargePerson",
              rules: [{
                required: true,
                message: "请输入负责人",
                trigger: "blur"
              }]
            },
            {
              label: "负责人职位",
              prop: "position",
              rules: [{
                required: true,
                message: "请输入负责人职位",
                trigger: "blur"
              }]
            },
            {
              label: "负责人电话",
              prop: "phone",
              rules: [{
                required: true,
                message: "请输入负责人电话",
                trigger: "blur"
              }]
            },
            {
              label: "负责人邮箱",
              prop: "email",
              rules: [{
                required: true,
                message: "请输入负责人邮箱",
                trigger: "blur"
              }]
            },
            {
              label: "创建者",
              prop: "createBy",
              hide:true,
              rules: [{
                required: false,
                message: "请输入创建者",
                trigger: "blur"
              }]
            },
            {
              label: "创建时间",
              prop: "createDate",
              hide:true,
              rules: [{
                required: false,
                message: "请输入创建时间",
                trigger: "blur"
              }]
            },
            {
              label: "修改者",
              prop: "updateBy",
              hide:true,
              rules: [{
                required: false,
                message: "请输入修改者",
                trigger: "blur"
              }]
            },
            {
              label: "修改时间",
              prop: "updateDate",
              hide:true,
              rules: [{
                required: false,
                message: "请输入修改时间",
                trigger: "blur"
              }]
            },
            {
              label: "备注信息",
              prop: "remarks",
              hide:true,
              rules: [{
                required: false,
                message: "请输入备注信息",
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
          addBtn: this.vaildData(this.permission.enterprise_add, false),
          viewBtn: this.vaildData(this.permission.enterprise_view, false),
          delBtn: this.vaildData(this.permission.enterprise_delete, false),
          editBtn: this.vaildData(this.permission.enterprise_edit, false)
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
      submitService(){
        const serviceLIst = this.$refs.tree.getCheckedKeys().join(",");	  
        grantService(this.rowid, serviceLIst).then(() => {
          this.box = false;
          this.$message({
            type: "success",
            message: "操作成功!"
          });
          this.onLoad(this.page);
        });
      },	
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
      handleService(row) {
        this.rowid=row.id;
        this.defaultObj = [];
        grantServiceTree()
          .then(res => {
            this.list = res.data.data;
            return getEnterpriseService(row.id);
          })
          .then(res => {
            this.defaultObj = res.data.data;
            this.box = true;
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
