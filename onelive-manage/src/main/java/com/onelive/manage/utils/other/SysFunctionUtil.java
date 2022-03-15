package com.onelive.manage.utils.other;


import com.onelive.common.model.vo.sys.SysFunctionVO;
import com.onelive.common.model.vo.sys.SysRoleFunctionVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lorenzo
 * @Description: 系统模块工具
 * @date 2021/4/6
 */
public class SysFunctionUtil {

    // List转树装结构逻辑
    public static List<SysFunctionVO> listToTree(List<SysFunctionVO> arr) {
        List<SysFunctionVO> root = new ArrayList<>();
        for (SysFunctionVO vo: arr) {
            if (vo.getParentFuncId() == null) {
                root.add(vo);
            }
        }
        Map<Long, List<SysFunctionVO>> hash = new HashMap<>();
        for (SysFunctionVO vo: arr) {
            hash.putIfAbsent(vo.getParentFuncId(), new ArrayList<>());
            hash.get(vo.getParentFuncId()).add(vo);
        }

        for (SysFunctionVO vo: root) {
            List<SysFunctionVO> children = hash.get(vo.getFuncId());
            if (children != null && !children.isEmpty()) {
                vo.setChildren(children);
                for (SysFunctionVO c: children) {
                    List<SysFunctionVO> cchildren = hash.get(c.getFuncId());
                    if (cchildren != null && !cchildren.isEmpty()) {
                        c.setChildren(cchildren);
                    }
                }
            }
        }

        return root;
    }

    private static List<SysFunctionVO> deepTree(Long pid, Map<Long, SysFunctionVO> hash, List<SysFunctionVO> arr) {
        List<SysFunctionVO> r = new ArrayList<>();
        for (SysFunctionVO vo: arr) {
            if (pid == null || pid.equals(vo.getParentFuncId())) {
                r.add(vo);
            } else {
                SysFunctionVO p = hash.get(vo.getParentFuncId());
                if (p != null) {
                    if (p.getChildren() != null) {
                        p.getChildren().add(vo);
                    } else {
                        List<SysFunctionVO> children = new ArrayList<>();
                        children.add(vo);
                        p.setChildren(children);
                    }
                }
            }
        }
        return r;
    }

    public static List<SysRoleFunctionVO> roleFuncListToTree(List<SysRoleFunctionVO> arr) {
        List<SysRoleFunctionVO> r = new ArrayList<>();
        Map<Long, SysRoleFunctionVO> hash = new HashMap<>();
        for (SysRoleFunctionVO vo: arr) {
            hash.put(vo.getFuncId(), vo);
        }
        for (SysRoleFunctionVO vo: arr) {
            if (vo.getParentFuncId() == null) {
                r.add(vo);
            } else {
                SysRoleFunctionVO p = hash.get(vo.getParentFuncId());
                if (p != null) {
                    if (p.getChildren() != null) {
                        p.getChildren().add(vo);
                    } else {
                        List<SysRoleFunctionVO> children = new ArrayList<>();
                        children.add(vo);
                        p.setChildren(children);
                    }
                }
            }
        }
        return r;
    }

}
