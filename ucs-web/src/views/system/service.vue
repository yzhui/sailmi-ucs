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
                   v-if="permission.service_delete"
                   @click="handleDelete">删 除
        </el-button>
      </template>
     <template slot-scope="scope" slot="menu">
        <el-button
          type="text"
          icon="el-icon-circle-plus-outline"
          size="small"
          @click.stop="handleMenu(scope.row,scope.index)"
        >功能定制
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
                   @click="submitMenu">确 定</el-button>
      </span>
    </el-dialog>
  </basic-container>
</template>

<script>
  import {grantServiceMenuTree} from "@/api/system/role";
  import {getList, getDetail,getServiceMenus,grant, add, update, remove} from "@/api/system/service";
  import {mapGetters} from "vuex";

  export default {
    data() {
      return {
        rowid: "",	  
        form: {},
        box: false,
        props: {
          label: "name",
          value: "id"
        },
        list: [],
        defaultObj: [],				
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
          index: false,
          viewBtn: true,
          selection: true,
          column: [
            {
              label: "主键",
              prop: "id",
              rules: [{
                required: true,
                message: "请输入",
                trigger: "blur"
              }]
            },
            {
              label: "服务名称",
              prop: "serviceName",
              rules: [{
                required: true,
                message: "请输入",
                trigger: "blur"
              }]
            },
			{
              label: "所属系统",
              prop: "systemId",
              type: "tree",
              dicUrl: "/api/sailmi-system/system/systemlist",
              hide:true,
              props: {
                label: "systemName",
                value: "id"
              },
              addDisplay: true,
              editDisplay: true,
              search: false,
              rules: [{
                required: true,
                message: "请输入所属系统",
              }]
            },
			{
              label: "所属系统",
              prop: "systemName",
              addDisplay: false,
              editDisplay: false,
              search: false,
            },
            {
              label: "服务类型",
              prop: "serviceType",
              hide:true,
              rules: [{
                required: true,
                message: "请输入服务类型",
                trigger: "blur"
              }]
            },
			{
              label: "服务类型",
              prop: "serviceTypeName",
              addDisplay: false,
              editDisplay: false,
              search: false,
            },
            {
              label: "创建人",
              prop: "createUser",
              hide:true,
              rules: [{
                required: true,
                message: "请输入创建人",
                trigger: "blur"
              }]
            },
			{
              label: "创建人",
              prop: "createName",
              addDisplay: false,
              editDisplay: false,
              search: false,
            },
            {
              label: "创建时间",
              prop: "createTime",
              rules: [{
                required: true,
                message: "请输入创建时间",
                trigger: "blur"
              }]
            },
            {
              label: "修改人",
              prop: "updateUser",
              hide:true,
              rules: [{
                required: true,
                message: "请输入修改人",
                trigger: "blur"
              }]
            },
			{
              label: "修改人",
              prop: "updateName",
              addDisplay: false,
              editDisplay: false,
              search: false,
            },
            {
              label: "修改时间",
              prop: "updateTime",
              rules: [{
                required: true,
                message: "请输入修改时间",
                trigger: "blur"
              }]
            },
            {
              label: "状态",
              prop: "status",
              rules: [{
                required: true,
                message: "请输入状态",
                trigger: "blur"
              }]
            },
            {
              label: "是否已删除",
              prop: "isDeleted",
              hide:true,
              rules: [{
                required: true,
                message: "请输入是否已删除",
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
          addBtn: this.vaildData(this.permission.service_add, false),
          viewBtn: this.vaildData(this.permission.service_view, false),
          delBtn: this.vaildData(this.permission.service_delete, false),
          editBtn: this.vaildData(this.permission.service_edit, false)
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
      submitMenu(){
        const menuLIst = this.$refs.tree.getCheckedKeys().join(",");	  
        grant(this.rowid, menuLIst).then(() => {
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
      handleMenu(row) {	  
		this.rowid=row.id;
        this.defaultObj = [];
        grantServiceMenuTree()
          .then(res => {
            this.list = res.data.data;
            return getServiceMenus(row.id);
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
