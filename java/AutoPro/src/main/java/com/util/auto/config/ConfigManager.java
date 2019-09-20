package com.util.auto.config;

import com.util.auto.model.Entity;
import com.util.auto.model.Project;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class ConfigManager {
    public static final String TEMPLATEROOT = System.getProperty("user.dir")+"\\src\\main\\resources\\template";
    public static final String PROJECTPATH = "project.projectpath";
    public static final String PACKAGEPATH = "project.packagepath";
    public static final String ENTITYNAME = "entity.name";
    public static final String VMPATH = "vm.path";
    public static final String VMNAME = "vm.name"; //vm文件名，用.分隔

    public static Config getConfigs() {
        return makeConfigs(getConfigFromXml());
    }

    public static Config getConfigFromXml() {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance("com.util.auto.config");
            Unmarshaller u = jc.createUnmarshaller();
            String xmlFilePath = System.getProperty("user.dir")+"\\src\\main\\resources\\config\\config.xml";

            System.out.println("配置文件:"+xmlFilePath);
            Config config = (Config) u.unmarshal(new File(xmlFilePath));
            return config;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static Config makeConfigs(Config config){
        List<VmConfig> vmConfigs = new ArrayList<VmConfig>();
        if(config !=null && config.getVmconfigs() !=null){
            for (VmConfig vmconfig :config.getVmconfigs()) {
                String templatepath = TEMPLATEROOT +File.separator+ config.vmroot +File.separator+ vmconfig.getVmpath();
                vmconfig.setVmpath(templatepath);
                if(vmconfig.isdir){
                    List<VmConfig> children = new ArrayList<VmConfig>();
                    File dir = new File(templatepath);
                    if(dir.exists() && dir.isDirectory()){
                        for (File file : dir.listFiles()) {
                            if(file.isFile()){
                                String filename = file.getName();
                                VmConfig temp = new VmConfig();
                                try {
                                    BeanUtils.copyProperties(temp,vmconfig);
                                    temp.setVmpath(templatepath);
                                    temp.setVmname(filename);
                                    temp.setIsdir(false);
                                    children.add(changeVMParams(temp));
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if(children.size()>0){
                        vmconfig.setChildren(children);;
                    }
                }else{
                    changeVMParams(vmconfig);
                }
            }
        }
        return config;
    }

    public static VmConfig changeVMParams(VmConfig vmConfig){
        String filename = vmConfig.getVmname();
        String[] names = filename.split("[.]");
        int dotnum = names.length-1;
        String vmpath = "";
        String vmname = "";
        if(names.length<=3){
            vmname = filename.substring(0,filename.lastIndexOf("."));
        }else{
            vmname = String.join(".", Arrays.copyOfRange(names,dotnum-2,dotnum));
            vmpath =String.join("/", Arrays.copyOfRange(names,0,dotnum-2));
        }

        String filepath = vmConfig.getOutfile().trim().replace(" ","");
        filepath = filepath.replace("{"+VMNAME+"}",vmname).replace("{"+VMPATH+"}",vmpath);
        vmConfig.setOutfile(filepath);

        return  vmConfig;
    }

    public static String getOutFile(VmConfig vmConfig, Project project,Entity entity){
        String filepath = vmConfig.getOutfile().trim().replace(" ","");
        String projectpath = "";
        String packagepath = "";
        String entityname = "";
        if(project != null){
             projectpath = project.getProjectpath();
             packagepath = project.getPackagepath();
        }
        if(entity != null ){
            entityname = entity.getName();
        }

        filepath = filepath.replace("{"+PROJECTPATH+"}",projectpath);

        filepath = filepath.replace("{"+PACKAGEPATH+"}",packagepath);

        if("entity".equals(vmConfig.type)){
            filepath = filepath.replace("{"+ENTITYNAME+"}",entityname);
        }

        return  filepath;
    }


}
