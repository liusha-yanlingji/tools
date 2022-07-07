package com.smartinsight.testcontroller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smartinsight.Mapper.FullTextMapper;
import com.smartinsight.Mapper.ImagesClsMapper;
import com.smartinsight.Mapper.ImagesFieidMapper;
import com.smartinsight.datasource.aspectj.DataSource;
import com.smartinsight.datasource.datasource.DynamicDataSource;
import com.smartinsight.datasource.enums.DataSourceType;
import com.smartinsight.datasource.utils.DynamicDataSourceContextHolder;
import com.smartinsight.model.FullText;
import com.smartinsight.model.ImagesCls;
import com.smartinsight.model.ImagesFieid;

@RestController
public class TestController {
    @Autowired
    DynamicDataSource dynamicDataSource;
    @Autowired
    FullTextMapper fullTextMapper;
    @Autowired
    ImagesClsMapper imagesClsMapper;
    @Autowired
    ImagesFieidMapper imagesFieidMapper;
    /**
     * 选择数据库示例
     */
    @DataSource(value = DataSourceType.SUANFA)
    public String getFullbyId() {
        return "1";
    }

    @GetMapping("/teee")
    public String str(){
    	
		/**选取数据源*/
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SUANFA.name());
        
        //分组查询源数据库中的数据信息
    	QueryWrapper<FullText> queryWrapper = new QueryWrapper<>();
    	queryWrapper.select("claim_clinic_map,claim_province_map");
    	queryWrapper.groupBy("claim_clinic_map", "claim_province_map");
    	List<FullText> selectList = fullTextMapper.selectList(queryWrapper);
    	
    	//根据分组查询的数据查询同步数据库数据
    	for (FullText fullText : selectList) {
    		String claimClinicMap = fullText.getClaimClinicMap();
    		String claimProvinceMap = fullText.getClaimProvinceMap();
    		//查询源数据信息
    	    QueryWrapper<FullText> queryFullWrapper = new QueryWrapper<>();
    	    Map<String, Object> fullMp = new HashMap<>();
    	    fullMp.put("claim_province_map", claimProvinceMap);
    	    fullMp.put("claim_clinic_map", claimClinicMap);
    		queryFullWrapper.allEq(fullMp);
    		List<FullText> list = fullTextMapper.selectList(queryFullWrapper);
    		
    		/**销毁数据源*/
            DynamicDataSourceContextHolder.clearDataSourceType();
    		
            
            /**选取数据源*/
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SUANFA.name());
    	    //选取数据源
    		DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.RESDEV.name());
    		QueryWrapper<ImagesCls> qw = new QueryWrapper<>();
    		Map<String, Object> map = new HashMap<>();
    		map.put("p_cls", claimProvinceMap);
    		map.put("code", claimClinicMap);
    		qw.allEq(map);
    		List<ImagesCls> selectList2 = imagesClsMapper.selectList(qw);
    		//获取查询到的数据id列 并查询相关需要同步的数据
    		Long id = selectList2.get(0).getId();
    		QueryWrapper<ImagesFieid> queryFieidWrapper = new QueryWrapper<>();
    		Map<String, Object> Fieidmap = new HashMap<>();
    		Fieidmap.put("cls_id", id);
    		List<ImagesFieid> selectList3 = imagesFieidMapper.selectList(queryFieidWrapper);
    	    //查询到目标数据库信息
    	    //比较两个list中不同的数据并处理
    		A:for (int i = 0; i < list.size(); i++) {
    			int num = 0;
				B:for (int j = 0; j < selectList3.size(); j++) {
					//筛除已存在数据
					if(list.get(i).getName().equals(selectList3.get(j).getFieldName())) {
						break B;
					}else {
					 /**
					  * 判断为新增数据
					  */
					ImagesFieid fieid = new ImagesFieid();
					fieid.setId(1L);
					fieid.setClsId(id);
					fieid.setFieldName(list.get(i).getName());
					if(list.get(i).getNameType().equals("float")) {
						fieid.setIsMoney((byte)1);
					}else {
						fieid.setIsMoney((byte)0);
					}
					fieid.setIsTable((byte)0);
					ImagesFieid imagesFieid = selectList3.stream().max(Comparator.comparing(ImagesFieid::getSerialNo)).get();
					fieid.setSerialNo(imagesFieid.getSerialNo()+10);
					fieid.setCreatedBy("roboot");
					fieid.setModifiedBy("roboot");
					fieid.setCreatedTime(new Date());
					fieid.setModifiedTime(new Date());
					fieid.setIsDel((byte)0);
					 //执行添加方法
//					imagesFieidMapper.insert(fieid);
						
					}
				}
				
				
			}
		}
    	
        DynamicDataSource a11 = dynamicDataSource;
        return "";
    }
    
    @GetMapping("/tees")
    public String tees(){
    	
        //添加数据需要存放的list
    	List<ImagesFieid> fieids = new ArrayList<>();
    	
		/**选取数据源*/
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SUANFA.name());
        //查询所有数据源数据返回
        QueryWrapper<FullText> queryWrapper = new QueryWrapper<>();
        List<FullText> fullList = fullTextMapper.selectList(queryWrapper);
        /**销毁数据源*/
        DynamicDataSourceContextHolder.clearDataSourceType();
        //构造分组后的map
        Map<String, List<FullText>> groupList = this.groupList(fullList);
        
        groupList.forEach((key, value) -> {
        	Collection<FullText> valueCollection = value;
        	List<FullText> valueList = new ArrayList<FullText>(valueCollection);
        	Map<String, FullText> idfuMap = getIdfuMap(valueList);
        	/**选取数据源*/
    		DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.RESDEV.name());
    		QueryWrapper<ImagesCls> qw = new QueryWrapper<>();
    		Map<String, Object> map = new HashMap<>();
    		map.put("p_cls", valueList.get(0).getClaimProvinceMap());
    		map.put("code", value.get(0).getClaimClinicMap());
    		qw.allEq(map);
    		List<ImagesCls> selectList2 = imagesClsMapper.selectList(qw);
    		//获取查询到的数据id列 并查询相关需要同步的数据
    		Long id = selectList2.get(0).getId();
    		QueryWrapper<ImagesFieid> queryFieidWrapper = new QueryWrapper<>();
    		Map<String, Object> Fieidmap = new HashMap<>();
    		Fieidmap.put("cls_id", id);
    		List<ImagesFieid> selectList3 = imagesFieidMapper.selectList(queryFieidWrapper);
    		/**销毁数据源*/
            DynamicDataSourceContextHolder.clearDataSourceType();
    	    //查询到目标数据库信息
    		for (int j = 0; j < selectList3.size(); j++) {
    			FullText fullText = idfuMap.get(selectList3.get(j).getFieldName());
				//筛除已存在数据
				if(fullText!=null) {
					continue;
				}else {
				 /**
				  * 判断为新增数据
				  */
				ImagesFieid fieid = new ImagesFieid();
				fieid.setId(1L);
				fieid.setClsId(id);
				fieid.setFieldName(fullText.getName());
				if(fullText.getNameType().equals("float")) {
					fieid.setIsMoney((byte)1);
				}else {
					fieid.setIsMoney((byte)0);
				}
				fieid.setIsTable((byte)0);
				ImagesFieid imagesFieid = selectList3.stream().max(Comparator.comparing(ImagesFieid::getSerialNo)).get();
				fieid.setSerialNo(imagesFieid.getSerialNo()+10);
				fieid.setCreatedBy("roboot");
				fieid.setModifiedBy("roboot");
				fieid.setCreatedTime(new Date());
				fieid.setModifiedTime(new Date());
				fieid.setIsDel((byte)0);
				 //存放到相关的list中
				fieids.add(fieid);
					
				}
    		}
        });

        try {
            // 选取数据源
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.RESDEV.name());
//            for (ImagesFieid imagesFieid : fieids) {
//            	imagesFieidMapper.insert(imagesFieid);
//			}
            //执行添加操作
        } finally {
            // 销毁数据源
            DynamicDataSourceContextHolder.clearDataSourceType();
        }

        try {
            // 切换到另一个数据源
            DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.RES.name());
            // 执行添加操作
//            for (ImagesFieid imagesFieid : fieids) {
//            	imagesFieidMapper.insert(imagesFieid);
//			}
        } finally {
            // 销毁数据源
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
        return "";
    	
    }
    
    //map分组
    public Map<String, List<FullText>> groupList(List<FullText> list){
    	Map<String, List<FullText>> map = list.stream().collect(
                Collectors.groupingBy(a -> format("{0}#{1}", a.getClaimClinicMap(),a.getClaimProvinceMap())));
		return map;
    }
    
    public static String format(String value, Object... paras) {
        return MessageFormat.format(value, paras);
    }
    
    //转map
    public Map<String, FullText> getIdfuMap(List<FullText> fuLists) {
        return fuLists.stream().collect(Collectors.toMap(FullText::getName, fuList -> fuList));
    }

}
