package bemlyGPT;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatMessage;
import com.azure.ai.openai.models.ChatRole;
import com.azure.ai.openai.models.Choice;
import com.azure.ai.openai.models.Completions;
import com.azure.ai.openai.models.CompletionsOptions;
import com.azure.ai.openai.models.CompletionsUsage;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.exception.HttpResponseException;
import com.azure.core.http.HttpClient;
import com.azure.core.util.Header;
import com.azure.core.util.HttpClientOptions;
import com.azure.core.util.IterableStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

public class AzureGPT {

    public static void main(String[] args) {
        String azureOpenaiKey = "";	// 密钥
        String endpoint = "";	// 终结点
        String modelId = "";	// GPT3.5模型id

        OpenAIClient client = new OpenAIClientBuilder()
        	.httpClient(HttpClient.createDefault(new HttpClientOptions()
        	.setHeaders(Collections.singletonList(new Header("Accept-Charset", "utf-8")))))
            .endpoint(endpoint)
            .credential(new AzureKeyCredential(azureOpenaiKey))
            .buildClient();
        

        List<ChatMessage> chatMsgList = new ArrayList<>();
        chatMsgList.add(new ChatMessage(ChatRole.SYSTEM).setContent("今天天气如何"));
        try {
        	IterableStream<ChatCompletions> completionsStream = client.getChatCompletionsStream(modelId, new ChatCompletionsOptions(chatMsgList));
        	completionsStream.forEach(completion -> {
    		    for (ChatChoice choice : completion.getChoices()) {
    		    	try { Thread.sleep(100); } catch (Exception e) { e.printStackTrace(); }
    		        ChatMessage message = choice.getDelta();
    		        if (message != null && message.getContent() != null) {
    		            System.out.print(message.getContent());
    		        }
    		    }
    		});
        } catch (HttpResponseException e) {
			JOptionPane.showMessageDialog(null, e, "警告：违反OpenAI GPT发言规定！", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
        }
    }
}


/*
 * 电子包浆库	需要的时候自取	是从GithubIssue的错误提交处于open状态里面专挑的代码
 */
//ChatCompletions chatCompletions = client.getChatCompletions(modelId, new ChatCompletionsOptions(chatMessages));

//System.out.printf("模型id:%s 创建于 %d.%n", chatCompletions.getId(), chatCompletions.getCreated());
//for (ChatChoice choice : chatCompletions.getChoices()) {
//  ChatMessage message = choice.getMessage();
//  System.out.printf("索引: %d, 聊天权限: %s.%n", choice.getIndex(), message.getRole());
//  System.out.println("消息:");
//  System.out.println(message.getContent());
//}
//
//System.out.println();
//CompletionsUsage usage = chatCompletions.getUsage();
//System.out.printf("使用量: (prompt token)提示参数令牌数量为  %d, "
//      + "完成令牌数量为 %d, 请求和响应中的总令牌数量为 %d.%n",
//  usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
//  
//  
//  System.out.println();
//  System.out.println();
//  System.out.println();
//  System.out.println();
//  
//  chatMessages.add(new ChatMessage(ChatRole.ASSISTANT).setContent("我上句话说的什么"));
//  chatCompletions = client.getChatCompletions(modelId, new ChatCompletionsOptions(chatMessages));
//  System.out.printf("模型id:%s 创建于 %d.%n", chatCompletions.getId(), chatCompletions.getCreated());
//  for (ChatChoice choice : chatCompletions.getChoices()) {
//      ChatMessage message = choice.getMessage();
//      System.out.printf("索引: %d, 聊天权限: %s.%n", choice.getIndex(), message.getRole());
//      System.out.println("消息:");
//      System.out.println(message.getContent());
//  }
//
//  System.out.println();
//  usage = chatCompletions.getUsage();
//  System.out.printf("使用量: (prompt token)提示参数令牌数量为  %d, "
//          + "完成令牌数量为 %d, 请求和响应中的总令牌数量为 %d.%n",
//      usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
  
//  List<ChatMessage> chatMessages = new ArrayList<>();
//  chatMessages.add(new ChatMessage(ChatRole.ASSISTANT).setContent("帮我写一个JDBC的代码，需要实现数据库mydb中user表name和pwd的增删改查"));
//  ChatCompletions chatCompletions = client.getChatCompletions(modelId, new ChatCompletionsOptions(chatMessages));
//  System.out.printf("模型id:%s 创建于 %d.%n", chatCompletions.getId(), chatCompletions.getCreated());
//  for (ChatChoice choice : chatCompletions.getChoices()) {
//      ChatMessage message = choice.getMessage();
//      System.out.printf("索引: %d, 聊天权限: %s.%n", choice.getIndex(), message.getRole());
//      System.out.println("消息:");
//      System.out.println(message.getContent());
//  }

//  List<String> prompt = new ArrayList<>();
//  prompt.add("");